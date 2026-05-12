package com.Ecommerce.Ecommerce.website.repository.tenant;

import com.Ecommerce.Ecommerce.website.entity.tenant.Payment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByOrder_Id(UUID orderId);
}

