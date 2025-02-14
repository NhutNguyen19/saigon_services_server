package com.iuh.edu.fit.service.implement;

import com.iuh.edu.fit.dto.request.*;
import com.iuh.edu.fit.dto.response.AuthenticationResponse;
import com.iuh.edu.fit.dto.response.IntrospectResponse;
import com.iuh.edu.fit.exception.AppException;
import com.iuh.edu.fit.exception.ErrorCode;
import com.iuh.edu.fit.model.InvalidatedToken;
import com.iuh.edu.fit.model.User;
import com.iuh.edu.fit.repository.InvalidatedTokenRepository;
import com.iuh.edu.fit.repository.UserRepository;
import com.iuh.edu.fit.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException | JOSEException | ParseException e) {
            isValid = false;
        }
        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(VALID_DURATION, ChronoUnit.SECONDS);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.UNAUTHORIZED);
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .expiryTime(Date.from(expiryDate))
                .build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signeToken = verifyToken(request.getToken(), true);
            String jit = signeToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signeToken.getJWTClaimsSet().getExpirationTime();
            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryDate(expiryTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.info("Token already expired");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signJWT = verifyToken(request.getToken(), true);
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        // Nếu token vẫn còn hạn thì mới thu hồi
        if (expiryTime.after(new Date())) {
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit).expiryDate(expiryTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
            log.info("Token {} has been invalidated", jit);
        } else {
            log.warn("Token expired, no need to invalidate.");
        }

        var username = signJWT.getJWTClaimsSet().getSubject();
        log.info("User name is: {}", username);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        var newToken = generateToken(user);
        return AuthenticationResponse.builder()
                .token(newToken)
                .expiryTime(expiryTime)
                .build();
    }


    @Override
    public String resetPassword(ResetPasswordRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        System.out.println("Reset password request for phone: " + request.getPhone() + ", username: " + request.getUsername());

        User user = userRepository.findByPhoneAndUsername(request.getPhone(), request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.PHONE_USERNAME_EXISTED));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return null;
    }

    private String generateToken(User user) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(VALID_DURATION, ChronoUnit.SECONDS);
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("nhujwt.com")
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiryDate))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
            });
        return stringJoiner.toString();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        log.info("Verifying token: {}", token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        log.info("Token expiry time: {}", expiryTime);

        boolean verified = signedJWT.verify(verifier);
        log.info("Token signature valid: {}", verified);
        if (!verified) {
            log.error("Token is invalid!");
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if (expiryTime.before(new Date())) {
            if (isRefresh) {
                log.warn("Token expired but allowed to refresh.");
            } else {
                log.error("Token is expired!");
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        }
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        if (invalidatedTokenRepository.existsById(jit)) {
            log.error("Token {} has been invalidated!", jit);
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        log.info("Token is valid!");
        return signedJWT;
    }
}
