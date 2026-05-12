package com.Ecommerce.Ecommerce.website.dto.request;

import jakarta.validation.constraints.NotBlank;

public class OrderStatusRequest {
    @NotBlank
    private String status;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

