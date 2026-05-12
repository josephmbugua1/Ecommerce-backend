package com.Ecommerce.Ecommerce.website.controller;

import com.Ecommerce.Ecommerce.website.service.PaymentService;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<?> initiate(@RequestBody Map<String, String> payload,
                                      @RequestParam("orderId") UUID orderId,
                                      @RequestParam(value = "provider", defaultValue = "stripe") String provider) {
        return ResponseEntity.ok(paymentService.initiate(orderId, provider));
    }

    @PostMapping("/webhook/stripe")
    public ResponseEntity<Void> stripeWebhook(@RequestBody Map<String, Object> payload) {
        paymentService.stripeWebhook(payload);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/webhook/mpesa")
    public ResponseEntity<Void> mpesaWebhook(@RequestBody Map<String, Object> payload) {
        paymentService.mpesaWebhook(payload);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> paymentStatus(@PathVariable UUID orderId) {
        return ResponseEntity.ok(paymentService.getByOrder(orderId));
    }
}

