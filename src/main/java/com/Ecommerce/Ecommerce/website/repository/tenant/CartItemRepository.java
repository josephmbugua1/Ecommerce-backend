package com.Ecommerce.Ecommerce.website.repository.tenant;

import com.Ecommerce.Ecommerce.website.entity.tenant.CartItem;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCart_Id(UUID cartId);
    void deleteByCart_Id(UUID cartId);
}

