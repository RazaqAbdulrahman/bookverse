package com.bookverse.backend.controller;

import com.bookverse.backend.dto.ApiResponse;
import com.bookverse.backend.dto.AuthRequest;
import com.bookverse.backend.dto.AuthResponse;
import com.bookverse.backend.dto.RegisterRequest;
import com.bookverse.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }
}