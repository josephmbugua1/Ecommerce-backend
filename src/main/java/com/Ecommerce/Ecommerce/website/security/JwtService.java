package com.Ecommerce.Ecommerce.website.security;

import com.Ecommerce.Ecommerce.website.entity.shared.User;
import com.Ecommerce.Ecommerce.website.entity.tenant.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${app.security.jwt.secret:dev-secret-key-change-me}")
    private String secret;

    @Value("${app.security.jwt.expiration-seconds:3600}")
    private long expirationSeconds;

    @Value("${app.security.jwt.refresh-expiration-seconds:86400}")
    private long refreshExpirationSeconds;

    public String generateAccessToken(User user) {
        return buildToken(user.getEmail(), user.getId(), user.getRole(), user.getTenant() == null ? null : user.getTenant().getSlug(), expirationSeconds, "access");
    }

    public String generateRefreshToken(User user) {
        return buildToken(user.getEmail(), user.getId(), user.getRole(), user.getTenant() == null ? null : user.getTenant().getSlug(), refreshExpirationSeconds, "refresh");
    }

    public String generateCustomerToken(Customer customer) {
        return buildToken(customer.getEmail(), customer.getId(), "CUSTOMER", null, expirationSeconds, "access");
    }

    public String generateCustomerRefreshToken(Customer customer) {
        return buildToken(customer.getEmail(), customer.getId(), "CUSTOMER", null, refreshExpirationSeconds, "refresh");
    }

    private String buildToken(String subject, UUID id, String role, String tenantSlug, long seconds, String tokenType) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(subject)
                .claim("uid", id == null ? null : id.toString())
                .claim("role", role)
                .claim("tenant", tenantSlug)
                .claim("tokenType", tokenType)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(seconds)))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public UUID extractUserId(String token) {
        String uid = extractAllClaims(token).get("uid", String.class);
        return uid == null ? null : UUID.fromString(uid);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractTenantSlug(String token) {
        return extractAllClaims(token).get("tenant", String.class);
    }

    public boolean isTokenValid(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration != null && expiration.toInstant().isAfter(Instant.now());
    }

    private SecretKey getSigningKey() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to initialize JWT signing key", ex);
        }
    }
}

