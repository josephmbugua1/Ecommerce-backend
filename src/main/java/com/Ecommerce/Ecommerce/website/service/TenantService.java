package com.Ecommerce.Ecommerce.website.service;

import com.Ecommerce.Ecommerce.website.entity.shared.Tenant;
import com.Ecommerce.Ecommerce.website.exception.TenantNotFoundException;
import com.Ecommerce.Ecommerce.website.repository.shared.TenantRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public Tenant getSettings(String slug) {
        return tenantRepository.findBySlug(slug)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
    }

    public Tenant updateSettings(String slug, Map<String, String> payload) {
        Tenant tenant = getSettings(slug);
        if (payload.containsKey("name")) {
            tenant.setName(payload.get("name"));
        }
        if (payload.containsKey("logoUrl")) {
            tenant.setLogoUrl(payload.get("logoUrl"));
        }
        if (payload.containsKey("primaryColor")) {
            tenant.setPrimaryColor(payload.get("primaryColor"));
        }
        return tenantRepository.save(tenant);
    }

    public Tenant updatePaymentKeys(String slug, Map<String, String> payload) {
        return updateSettings(slug, payload);
    }

    public List<Tenant> allTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenant(UUID id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found"));
    }

    public void suspendTenant(UUID id) {
        Tenant tenant = getTenant(id);
        tenant.setActive(false);
        tenantRepository.save(tenant);
    }

    public void deleteTenant(UUID id) {
        tenantRepository.deleteById(id);
    }
}

