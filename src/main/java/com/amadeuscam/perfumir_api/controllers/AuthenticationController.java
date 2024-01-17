package com.amadeuscam.perfumir_api.controllers;

import com.amadeuscam.perfumir_api.dto.JwtAuthenticationResponse;
import com.amadeuscam.perfumir_api.dto.RefreshTokenRequest;
import com.amadeuscam.perfumir_api.dto.SignInRequest;
import com.amadeuscam.perfumir_api.dto.SignUpRequest;
import com.amadeuscam.perfumir_api.entities.User;
import com.amadeuscam.perfumir_api.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> singup(@RequestBody SignUpRequest signUpRequest) {
        final User signup = authenticationService.signup(signUpRequest);
        return new ResponseEntity<>(signup, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> singin(@RequestBody SignInRequest signInRequest) {
        final JwtAuthenticationResponse signin = authenticationService.signin(signInRequest);
        return new ResponseEntity<>(signin, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        final JwtAuthenticationResponse refreshToken = authenticationService.refreshToken(refreshTokenRequest);
        return new ResponseEntity<>(refreshToken, HttpStatus.OK);
    }

}
