package com.amadeuscam.perfumir_api.services.impl;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.amadeuscam.perfumir_api.dto.JwtAuthenticationResponse;
import com.amadeuscam.perfumir_api.dto.RefreshTokenRequest;
import com.amadeuscam.perfumir_api.dto.SignInRequest;
import com.amadeuscam.perfumir_api.dto.SignUpRequest;
import com.amadeuscam.perfumir_api.entities.Role;
import com.amadeuscam.perfumir_api.entities.User;
import com.amadeuscam.perfumir_api.repository.UserRepository;
import com.amadeuscam.perfumir_api.services.AuthenticationService;
import com.amadeuscam.perfumir_api.services.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstName());
        user.setSecondname(signUpRequest.getLastName());
        user.setRole((Objects.equals(signUpRequest.getRole(), "admin")) ? Role.ADMIN : Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(user);

    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {


        try {
            if (signInRequest.getEmail() == null || signInRequest.getPassword() == null) {
                throw new IllegalArgumentException("Email and password can't be null");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
            );
            var user = userRepository.findByEmail(
                            signInRequest.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or pasword"));

            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            jwtAuthenticationResponse.setToken(jwt);
            return jwtAuthenticationResponse;
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, exception.getMessage());
        }

    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);


            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            jwtAuthenticationResponse.setToken(jwt);
            return jwtAuthenticationResponse;
        }
        return null;

    }
}
