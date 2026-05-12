package com.Ecommerce.Ecommerce.website.service;

import com.Ecommerce.Ecommerce.website.dto.request.LoginRequest;
import com.Ecommerce.Ecommerce.website.dto.request.RegisterTenantRequest;
import com.Ecommerce.Ecommerce.website.dto.response.AuthResponse;
import com.Ecommerce.Ecommerce.website.entity.shared.SubscriptionPlan;
import com.Ecommerce.Ecommerce.website.entity.shared.Tenant;
import com.Ecommerce.Ecommerce.website.entity.shared.User;
import com.Ecommerce.Ecommerce.website.entity.tenant.Customer;
import com.Ecommerce.Ecommerce.website.exception.UnauthorizedException;
import com.Ecommerce.Ecommerce.website.multitenancy.TenantProvisioningService;
import com.Ecommerce.Ecommerce.website.repository.shared.SubscriptionPlanRepository;
import com.Ecommerce.Ecommerce.website.repository.shared.TenantRepository;
import com.Ecommerce.Ecommerce.website.repository.shared.UserRepository;
import com.Ecommerce.Ecommerce.website.repository.tenant.CustomerRepository;
import com.Ecommerce.Ecommerce.website.security.JwtService;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final CustomerRepository customerRepository;
    private final TenantProvisioningService tenantProvisioningService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            TenantRepository tenantRepository,
            UserRepository userRepository,
            SubscriptionPlanRepository subscriptionPlanRepository,
            CustomerRepository customerRepository,
            TenantProvisioningService tenantProvisioningService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.customerRepository = customerRepository;
        this.tenantProvisioningService = tenantProvisioningService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse registerTenant(RegisterTenantRequest request) {
        String slug = request.getSlug().trim().toLowerCase();
        tenantRepository.findBySlug(slug).ifPresent(t -> {
            throw new UnauthorizedException("Tenant slug already exists");
        });

        SubscriptionPlan plan = subscriptionPlanRepository.findByName(request.getPlanName())
                .orElseGet(() -> {
                    SubscriptionPlan p = new SubscriptionPlan();
                    p.setName(request.getPlanName());
                    p.setMaxProducts(100);
                    p.setMaxOrdersPerMonth(1000);
                    p.setPrice(java.math.BigDecimal.ZERO);
                    return subscriptionPlanRepository.save(p);
                });

        Tenant tenant = new Tenant();
        tenant.setSlug(slug);
        tenant.setName(request.getStoreName());
        tenant.setPlan(plan);
        Tenant savedTenant = tenantRepository.save(tenant);

        tenantProvisioningService.provisionTenantSchema("tenant_" + slug);

        User user = new User();
        user.setTenant(savedTenant);
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("TENANT_ADMIN");
        User saved = userRepository.save(user);

        return authFromUser(saved);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return authFromUser(user);
    }

    public AuthResponse registerCustomer(LoginRequest request) {
        Customer customer = new Customer();
        customer.setEmail(request.getEmail().trim().toLowerCase());
        customer.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        customer.setFullName(request.getEmail());
        Customer saved = customerRepository.save(customer);

        return new AuthResponse(
                jwtService.generateCustomerToken(saved),
                jwtService.generateCustomerRefreshToken(saved),
                "CUSTOMER",
                request.getTenantSlug());
    }

    public AuthResponse loginCustomer(LoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), customer.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return new AuthResponse(
                jwtService.generateCustomerToken(customer),
                jwtService.generateCustomerRefreshToken(customer),
                "CUSTOMER",
                request.getTenantSlug());
    }

    public AuthResponse refresh(String refreshToken) {
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new UnauthorizedException("Invalid refresh token");
        }
        UUID userId = jwtService.extractUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        return authFromUser(user);
    }

    private AuthResponse authFromUser(User user) {
        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user),
                user.getRole(),
                user.getTenant() == null ? null : user.getTenant().getSlug());
    }
}

