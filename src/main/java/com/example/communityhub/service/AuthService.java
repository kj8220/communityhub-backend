package com.example.communityhub.service;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.communityhub.dto.AuthResponse;
import com.example.communityhub.dto.LoginRequest;
import com.example.communityhub.dto.RegisterRequest;
import com.example.communityhub.entity.User;
import com.example.communityhub.exception.UserAlreadyExistsException;
import com.example.communityhub.repository.UserRepository;
import com.example.communityhub.security.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public void signup(RegisterRequest request) {
        User user = new User();
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new UserAlreadyExistsException("Username already exists");
            }
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated(Instant.now());
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = jwtProvider.generateToken(request.getUsername());
        return new AuthResponse(token, request.getUsername());
    }
}
