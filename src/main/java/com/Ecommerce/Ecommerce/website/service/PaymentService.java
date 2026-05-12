package com.Ecommerce.Ecommerce.website.service;

import com.Ecommerce.Ecommerce.website.entity.tenant.Order;
import com.Ecommerce.Ecommerce.website.entity.tenant.Payment;
import com.Ecommerce.Ecommerce.website.repository.tenant.OrderRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.PaymentRepository;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment initiate(UUID orderId, String provider) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        Payment payment = paymentRepository.findByOrder_Id(orderId).orElseGet(Payment::new);
        payment.setOrder(order);
        payment.setAmount(order.getTotal());
        payment.setProvider(provider);
        payment.setStatus("UNPAID");
        payment.setProviderReference(provider + "_" + orderId);
        return paymentRepository.save(payment);
    }

    public void stripeWebhook(Map<String, Object> payload) {
        Object ref = payload.get("provider_reference");
        if (ref != null) {
            paymentRepository.findAll().stream()
                    .filter(p -> ref.equals(p.getProviderReference()))
                    .findFirst()
                    .ifPresent(payment -> {
                        payment.setStatus("PAID");
                        payment.setPaidAt(OffsetDateTime.now());
                        paymentRepository.save(payment);
                    });
        }
    }

    public void mpesaWebhook(Map<String, Object> payload) {
        stripeWebhook(payload);
    }

    public Payment getByOrder(UUID orderId) {
        return paymentRepository.findByOrder_Id(orderId).orElseThrow();
    }
}

