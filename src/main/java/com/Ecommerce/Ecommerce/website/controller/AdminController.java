package com.Ecommerce.Ecommerce.website.controller;

import com.Ecommerce.Ecommerce.website.dto.response.DashboardStatsResponse;
import com.Ecommerce.Ecommerce.website.repository.shared.UserRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.OrderRepository;
import com.Ecommerce.Ecommerce.website.service.TenantService;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final TenantService tenantService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AdminController(TenantService tenantService,
                           UserRepository userRepository,
                           OrderRepository orderRepository) {
        this.tenantService = tenantService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/tenants")
    public ResponseEntity<?> allTenants() {
        return ResponseEntity.ok(tenantService.allTenants());
    }

    @GetMapping("/tenants/{id}")
    public ResponseEntity<?> tenantDetail(@PathVariable UUID id) {
        return ResponseEntity.ok(tenantService.getTenant(id));
    }

    @PutMapping("/tenants/{id}/suspend")
    public ResponseEntity<Void> suspend(@PathVariable UUID id) {
        tenantService.suspendTenant(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tenants/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> stats() {
        DashboardStatsResponse response = new DashboardStatsResponse();
        response.setTotalTenants(tenantService.allTenants().size());
        response.setTotalUsers(userRepository.count());
        response.setTotalOrders(orderRepository.count());
        response.setTotalGmv("0.00");
        return ResponseEntity.ok(response);
    }
}

