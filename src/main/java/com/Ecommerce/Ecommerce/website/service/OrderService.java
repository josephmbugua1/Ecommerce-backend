package com.Ecommerce.Ecommerce.website.service;

import com.Ecommerce.Ecommerce.website.dto.request.CheckoutRequest;
import com.Ecommerce.Ecommerce.website.dto.response.OrderResponse;
import com.Ecommerce.Ecommerce.website.entity.tenant.Cart;
import com.Ecommerce.Ecommerce.website.entity.tenant.CartItem;
import com.Ecommerce.Ecommerce.website.entity.tenant.Customer;
import com.Ecommerce.Ecommerce.website.entity.tenant.Order;
import com.Ecommerce.Ecommerce.website.entity.tenant.OrderItem;
import com.Ecommerce.Ecommerce.website.repository.tenant.CartItemRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.CartRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.CustomerRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.OrderItemRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(CustomerRepository customerRepository,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderResponse checkout(CheckoutRequest request) {
        Customer customer = customerRepository.findByEmail(request.getCustomerEmail()).orElseThrow();
        Cart cart = cartRepository.findByCustomer(customer).orElseThrow();
        List<CartItem> cartItems = cartItemRepository.findByCart_Id(cart.getId());

        BigDecimal total = cartItems.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setCustomer(customer);
        order.setShippingAddress(request.getShippingAddress());
        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setVariant(cartItem.getVariant());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteByCart_Id(cart.getId());

        return toResponse(savedOrder);
    }

    public List<OrderResponse> list(String customerEmail, boolean adminView) {
        if (adminView) {
            return orderRepository.findAll().stream().map(this::toResponse).toList();
        }

        Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow();
        return orderRepository.findByCustomer(customer).stream().map(this::toResponse).toList();
    }

    public OrderResponse get(UUID id) {
        return orderRepository.findById(id).map(this::toResponse).orElseThrow();
    }

    public OrderResponse updateStatus(UUID id, String status) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(status);
        return toResponse(orderRepository.save(order));
    }

    public void cancel(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus());
        response.setTotal(order.getTotal());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}

