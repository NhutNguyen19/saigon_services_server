package com.iuh.edu.fit.service;

import java.text.ParseException;

import com.iuh.edu.fit.dto.request.*;
import com.iuh.edu.fit.dto.response.AuthenticationResponse;
import com.iuh.edu.fit.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

    String resetPassword(ResetPasswordRequest request);
}
