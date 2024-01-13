package com.amadeuscam.perfumir_api.services;

import com.amadeuscam.perfumir_api.dto.JwtAuthenticationResponse;
import com.amadeuscam.perfumir_api.dto.RefreshTokenRequest;
import com.amadeuscam.perfumir_api.dto.SignInRequest;
import com.amadeuscam.perfumir_api.dto.SignUpRequest;
import com.amadeuscam.perfumir_api.entities.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
