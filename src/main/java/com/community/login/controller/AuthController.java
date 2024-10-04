package com.community.login.controller;

import com.community.login.service.AuthService;
import com.community.login.controller.dto.JwtAuthToken;
import com.community.login.controller.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<JwtAuthToken> login(@RequestBody LoginRequest request) {
        JwtAuthToken response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
