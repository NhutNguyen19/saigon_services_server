    package com.iuh.edu.fit.config;

    import java.text.ParseException;

    import jakarta.validation.constraints.NotNull;
    import lombok.experimental.NonFinal;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.security.oauth2.jwt.Jwt;
    import org.springframework.security.oauth2.jwt.JwtDecoder;
    import org.springframework.security.oauth2.jwt.JwtException;
    import org.springframework.stereotype.Component;

    import com.nimbusds.jwt.SignedJWT;

    @Component
    public class CustomJwtDecoder implements JwtDecoder {

        @Value("${jwt.signerKey}")
        @NonFinal
        private String signerKey;

        @Override
        public Jwt decode(String token) throws JwtException {

            try {
                SignedJWT signedJWT = SignedJWT.parse(token);

                return new Jwt(
                        token,
                        signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                        signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                        signedJWT.getHeader().toJSONObject(),
                        signedJWT.getJWTClaimsSet().getClaims());
            } catch (ParseException e) {
                throw new JwtException("Invalid Token");
            }
        }
    }
