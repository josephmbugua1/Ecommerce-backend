package com.Ecommerce.Ecommerce.website.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    public static class Item {
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;

        public Item() {
        }

        public Item(String productName, Integer quantity, BigDecimal unitPrice) {
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }

    private List<Item> items;
    private BigDecimal total;

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}

