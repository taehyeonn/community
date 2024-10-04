package com.community.login.controller;

import com.community.login.service.LoginService;
import com.community.login.controller.dto.JwtAuthToken;
import com.community.login.controller.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<JwtAuthToken> login(@RequestBody LoginRequest request) {
        JwtAuthToken response = loginService.login(request);
        return ResponseEntity.ok(response);
    }
}
