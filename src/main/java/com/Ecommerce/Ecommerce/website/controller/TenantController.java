package com.Ecommerce.Ecommerce.website.controller;

import com.Ecommerce.Ecommerce.website.service.TenantService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<?> getSettings(@RequestHeader("X-Tenant-Slug") String tenantSlug) {
        return ResponseEntity.ok(tenantService.getSettings(tenantSlug));
    }

    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestHeader("X-Tenant-Slug") String tenantSlug,
                                            @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(tenantService.updateSettings(tenantSlug, payload));
    }

    @PutMapping("/payment-keys")
    public ResponseEntity<?> updatePaymentKeys(@RequestHeader("X-Tenant-Slug") String tenantSlug,
                                               @RequestBody Map<String, String> payload) {
        return ResponseEntity.ok(tenantService.updatePaymentKeys(tenantSlug, payload));
    }
}

