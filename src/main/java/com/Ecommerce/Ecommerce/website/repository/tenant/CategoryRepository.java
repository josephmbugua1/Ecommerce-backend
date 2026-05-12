package com.Ecommerce.Ecommerce.website.repository.tenant;

import com.Ecommerce.Ecommerce.website.entity.tenant.Category;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}

