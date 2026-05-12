package com.Ecommerce.Ecommerce.website.controller;

import com.Ecommerce.Ecommerce.website.dto.request.CartItemRequest;
import com.Ecommerce.Ecommerce.website.service.CartService;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(@RequestHeader("X-Customer-Email") String customerEmail) {
        return ResponseEntity.ok(cartService.getCurrentCart(customerEmail));
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestHeader("X-Customer-Email") String customerEmail,
                                     @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItem(customerEmail, request));
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<?> updateItem(@PathVariable UUID id, @RequestBody Map<String, Integer> request) {
        return ResponseEntity.ok(cartService.updateItem(id, request.getOrDefault("quantity", 1)));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID id) {
        cartService.removeItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("X-Customer-Email") String customerEmail) {
        cartService.clearCart(customerEmail);
        return ResponseEntity.noContent().build();
    }
}

