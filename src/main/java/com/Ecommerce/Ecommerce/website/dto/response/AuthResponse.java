package com.Ecommerce.Ecommerce.website.dto.response;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String role;
    private String tenantSlug;

    public AuthResponse() {
    }

    public AuthResponse(String accessToken, String refreshToken, String role, String tenantSlug) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
        this.tenantSlug = tenantSlug;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getTenantSlug() { return tenantSlug; }
    public void setTenantSlug(String tenantSlug) { this.tenantSlug = tenantSlug; }
}

