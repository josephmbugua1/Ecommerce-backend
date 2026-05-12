package com.Ecommerce.Ecommerce.website.repository.shared;

import com.Ecommerce.Ecommerce.website.entity.shared.Tenant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    Optional<Tenant> findBySlug(String slug);
}

