package com.Ecommerce.Ecommerce.website.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterTenantRequest {
    @NotBlank
    private String slug;
    @NotBlank
    private String storeName;
    @NotBlank
    private String adminName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String planName = "free";

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
}

