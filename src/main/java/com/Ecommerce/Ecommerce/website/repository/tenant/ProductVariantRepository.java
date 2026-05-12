package com.Ecommerce.Ecommerce.website.repository.tenant;

import com.Ecommerce.Ecommerce.website.entity.tenant.ProductVariant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    List<ProductVariant> findByProduct_Id(UUID productId);
}

