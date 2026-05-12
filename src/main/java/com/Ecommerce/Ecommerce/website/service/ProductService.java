package com.Ecommerce.Ecommerce.website.service;

import com.Ecommerce.Ecommerce.website.dto.request.ProductRequest;
import com.Ecommerce.Ecommerce.website.dto.response.ProductResponse;
import com.Ecommerce.Ecommerce.website.entity.tenant.Product;
import com.Ecommerce.Ecommerce.website.entity.tenant.ProductVariant;
import com.Ecommerce.Ecommerce.website.exception.ProductNotFoundException;
import com.Ecommerce.Ecommerce.website.repository.tenant.CategoryRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.ProductRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.ProductVariantRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository productVariantRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ProductVariantRepository productVariantRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productVariantRepository = productVariantRepository;
    }

    public List<ProductResponse> list() {
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponse get(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return toResponse(product);
    }

    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        apply(product, request);
        return toResponse(productRepository.save(product));
    }

    public ProductResponse update(UUID id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        apply(product, request);
        return toResponse(productRepository.save(product));
    }

    public void delete(UUID id) {
        productRepository.deleteById(id);
    }

    public List<ProductVariant> variants(UUID productId) {
        return productVariantRepository.findByProduct_Id(productId);
    }

    public ProductVariant addVariant(UUID productId, ProductVariant variant) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        variant.setProduct(product);
        return productVariantRepository.save(variant);
    }

    private void apply(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setActive(Boolean.TRUE.equals(request.getActive()));
        if (request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId()).ifPresent(product::setCategory);
        }
    }

    private ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setImageUrl(product.getImageUrl());
        return response;
    }
}

