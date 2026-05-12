package com.Ecommerce.Ecommerce.website.controller;

import com.Ecommerce.Ecommerce.website.dto.request.CheckoutRequest;
import com.Ecommerce.Ecommerce.website.dto.request.OrderStatusRequest;
import com.Ecommerce.Ecommerce.website.service.OrderService;
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
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(orderService.checkout(request));
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestHeader(value = "X-Customer-Email", required = false) String customerEmail,
                                  @RequestHeader(value = "X-Admin-View", required = false, defaultValue = "false") boolean adminView) {
        return ResponseEntity.ok(orderService.list(customerEmail, adminView));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody OrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        orderService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}

