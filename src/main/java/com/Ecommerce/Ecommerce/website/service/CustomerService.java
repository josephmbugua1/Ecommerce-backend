package com.Ecommerce.Ecommerce.website.service;

import com.Ecommerce.Ecommerce.website.entity.tenant.Customer;
import com.Ecommerce.Ecommerce.website.repository.tenant.CustomerRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.OrderRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    public Customer get(UUID id) {
        return customerRepository.findById(id).orElseThrow();
    }

    public List<Map<String, Object>> orderHistory(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        return orderRepository.findByCustomer(customer).stream()
                .map(order -> Map.<String, Object>of(
                        "id", order.getId(),
                        "status", order.getStatus(),
                        "total", order.getTotal(),
                        "createdAt", order.getCreatedAt()))
                .toList();
    }
}

