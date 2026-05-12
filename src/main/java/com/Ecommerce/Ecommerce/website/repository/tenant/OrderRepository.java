package com.Ecommerce.Ecommerce.website.repository.tenant;

import com.Ecommerce.Ecommerce.website.entity.tenant.Customer;
import com.Ecommerce.Ecommerce.website.entity.tenant.Order;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomer(Customer customer);
}

