package com.bookverse.backend.service;

import com.bookverse.backend.dto.AuthRequest;
import com.bookverse.backend.dto.AuthResponse;
import com.bookverse.backend.dto.RegisterRequest;
import com.bookverse.backend.exception.EmailAlreadyExistsException;
import com.bookverse.backend.model.User;
import com.bookverse.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        // Create new user
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        // Save user to database
        User savedUser = userRepository.save(user);

        // Load user details and generate JWT token
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        // Build and return response
        return AuthResponse.builder()
                .token(jwtToken)
                .email(savedUser.getEmail())
                .userId(savedUser.getId())
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        // Authenticate user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Load user details and generate JWT token
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String jwtToken = jwtService.generateToken(userDetails);

        // Build and return response
        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .userId(user.getId())
                .build();
    }
}
