package com.Ecommerce.Ecommerce.website.entity.shared;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription_plans", schema = "public")
public class SubscriptionPlan {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private Integer maxProducts;
    private Integer maxOrdersPerMonth;
    private BigDecimal price;

    @Column(nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getMaxProducts() { return maxProducts; }
    public void setMaxProducts(Integer maxProducts) { this.maxProducts = maxProducts; }
    public Integer getMaxOrdersPerMonth() { return maxOrdersPerMonth; }
    public void setMaxOrdersPerMonth(Integer maxOrdersPerMonth) { this.maxOrdersPerMonth = maxOrdersPerMonth; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}

