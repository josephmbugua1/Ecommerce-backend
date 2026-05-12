package com.Ecommerce.Ecommerce.website.service;

import com.Ecommerce.Ecommerce.website.dto.request.CartItemRequest;
import com.Ecommerce.Ecommerce.website.dto.response.CartResponse;
import com.Ecommerce.Ecommerce.website.entity.tenant.Cart;
import com.Ecommerce.Ecommerce.website.entity.tenant.CartItem;
import com.Ecommerce.Ecommerce.website.entity.tenant.Customer;
import com.Ecommerce.Ecommerce.website.entity.tenant.Product;
import com.Ecommerce.Ecommerce.website.repository.tenant.CartItemRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.CartRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.CustomerRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public CartResponse getCurrentCart(String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow();
        Cart cart = cartRepository.findByCustomer(customer).orElseGet(() -> cartRepository.save(newCart(customer)));
        List<CartItem> items = cartItemRepository.findByCart_Id(cart.getId());
        return toResponse(items);
    }

    public CartResponse addItem(String customerEmail, CartItemRequest request) {
        Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow();
        Cart cart = cartRepository.findByCustomer(customer).orElseGet(() -> cartRepository.save(newCart(customer)));
        Product product = productRepository.findById(request.getProductId()).orElseThrow();

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);

        return toResponse(cartItemRepository.findByCart_Id(cart.getId()));
    }

    public CartResponse updateItem(UUID itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId).orElseThrow();
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return toResponse(cartItemRepository.findByCart_Id(item.getCart().getId()));
    }

    public void removeItem(UUID itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public void clearCart(String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail).orElseThrow();
        Cart cart = cartRepository.findByCustomer(customer).orElseThrow();
        cartItemRepository.deleteByCart_Id(cart.getId());
    }

    private Cart newCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        return cart;
    }

    private CartResponse toResponse(List<CartItem> items) {
        CartResponse response = new CartResponse();
        response.setItems(items.stream().map(item -> new CartResponse.Item(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getPrice())).toList());

        BigDecimal total = items.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotal(total);
        return response;
    }
}

