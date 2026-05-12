package com.Ecommerce.Ecommerce.website.repository.shared;

import com.Ecommerce.Ecommerce.website.entity.shared.SubscriptionPlan;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, UUID> {
    Optional<SubscriptionPlan> findByName(String name);
}

