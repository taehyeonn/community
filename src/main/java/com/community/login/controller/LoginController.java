package com.community.login.controller;

import com.community.login.controller.dto.AccessToken;
import com.community.login.service.LoginService;
import com.community.login.service.dto.JwtTokenDto;
import com.community.login.controller.dto.LoginRequest;
import com.community.login.controller.dto.RefreshAccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@RequestBody LoginRequest request) {
        JwtTokenDto response = loginService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, response.refreshTokenCookie().toString())
                .body(response.accessToken());
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshAccessToken> refresh(@CookieValue(name = "refreshToken") String refreshToken) {
        RefreshAccessToken response = loginService.refresh(refreshToken);
        return ResponseEntity.ok(response);
    }
}
