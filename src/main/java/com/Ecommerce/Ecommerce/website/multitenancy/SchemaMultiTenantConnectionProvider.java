package com.Ecommerce.Ecommerce.website.multitenancy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.stereotype.Component;

@Component
public class SchemaMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    private final DataSource dataSource;
    private final Map<String, DataSource> tenantDataSources = new ConcurrentHashMap<>();

    public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected DataSource selectAnyDataSource() {
        return dataSource;
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        return tenantDataSources.getOrDefault(tenantIdentifier, dataSource);
    }

    public void registerTenant(String tenantIdentifier, DataSource tenantDataSource) {
        tenantDataSources.put(tenantIdentifier, tenantDataSource);
    }
}

