package com.Ecommerce.Ecommerce.website.dto.request;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public class CartItemRequest {
    @NotNull
    private UUID productId;
    private UUID variantId;
    @NotNull
    private Integer quantity;

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public UUID getVariantId() { return variantId; }
    public void setVariantId(UUID variantId) { this.variantId = variantId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}

