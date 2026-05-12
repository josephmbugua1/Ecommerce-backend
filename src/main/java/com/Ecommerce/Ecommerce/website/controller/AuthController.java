package com.Ecommerce.Ecommerce.website.controller;

import com.Ecommerce.Ecommerce.website.dto.request.LoginRequest;
import com.Ecommerce.Ecommerce.website.dto.request.RegisterTenantRequest;
import com.Ecommerce.Ecommerce.website.dto.response.AuthResponse;
import com.Ecommerce.Ecommerce.website.service.AuthService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register-tenant")
    public ResponseEntity<AuthResponse> registerTenant(@Valid @RequestBody RegisterTenantRequest request) {
        return ResponseEntity.ok(authService.registerTenant(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/customer/register")
    public ResponseEntity<AuthResponse> registerCustomer(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.registerCustomer(request));
    }

    @PostMapping("/customer/login")
    public ResponseEntity<AuthResponse> loginCustomer(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginCustomer(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(authService.refresh(body.get("refreshToken")));
    }
}

