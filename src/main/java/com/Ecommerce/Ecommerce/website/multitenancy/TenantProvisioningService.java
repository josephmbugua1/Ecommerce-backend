package com.Ecommerce.Ecommerce.website.multitenancy;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TenantProvisioningService {

    private final JdbcTemplate jdbcTemplate;

    public TenantProvisioningService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void provisionTenantSchema(String schemaName) {
        String schema = sanitizeSchemaName(schemaName);
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS \"" + schema + "\"");
    }

    private String sanitizeSchemaName(String schemaName) {
        if (schemaName == null || !schemaName.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Invalid schema name");
        }
        return schemaName;
    }
}

