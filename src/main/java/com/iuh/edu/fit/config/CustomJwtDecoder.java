package com.iuh.edu.fit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    @NonFinal
    private String signerKey;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            log.info("Decoding JWT token: {}", token);
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

            if (!signedJWT.verify(verifier)) {
                log.error("JWT signature verification failed!");
                throw new JwtException("Invalid JWT signature");
            }

            return new Jwt(
                    token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().getClaims());
        } catch (Exception e) {
            log.error("Failed to decode JWT: {}", e.getMessage());
            throw new JwtException("Invalid token", e);
        }
    }
}
