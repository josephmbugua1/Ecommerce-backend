package com.Ecommerce.Ecommerce.website.repository.tenant;

import com.Ecommerce.Ecommerce.website.entity.tenant.Cart;
import com.Ecommerce.Ecommerce.website.entity.tenant.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomer(Customer customer);
}

