package com.Ecommerce.Ecommerce.website.controller;

import com.Ecommerce.Ecommerce.website.dto.request.ProductRequest;
import com.Ecommerce.Ecommerce.website.entity.tenant.ProductVariant;
import com.Ecommerce.Ecommerce.website.service.ProductService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(productService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN','TENANT_ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN','TENANT_ADMIN')")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN','TENANT_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/variants")
    public ResponseEntity<?> variants(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.variants(id));
    }

    @PostMapping("/{id}/variants")
    @PreAuthorize("hasAnyRole('PLATFORM_ADMIN','TENANT_ADMIN')")
    public ResponseEntity<?> addVariant(@PathVariable UUID id, @RequestBody ProductVariant variant) {
        return ResponseEntity.ok(productService.addVariant(id, variant));
    }
}

