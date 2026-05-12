package com.Ecommerce.Ecommerce.website.repository.shared;

import com.Ecommerce.Ecommerce.website.entity.shared.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}

