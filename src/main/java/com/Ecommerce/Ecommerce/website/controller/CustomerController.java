package com.Ecommerce.Ecommerce.website.controller;

import com.Ecommerce.Ecommerce.website.service.CustomerService;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(customerService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.get(id));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<?> orderHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.orderHistory(id));
    }
}

