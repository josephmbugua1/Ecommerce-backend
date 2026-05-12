package com.Ecommerce.Ecommerce.website.dto.response;

public class DashboardStatsResponse {
    private long totalTenants;
    private long totalUsers;
    private long totalOrders;
    private String totalGmv;

    public long getTotalTenants() { return totalTenants; }
    public void setTotalTenants(long totalTenants) { this.totalTenants = totalTenants; }
    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }
    public String getTotalGmv() { return totalGmv; }
    public void setTotalGmv(String totalGmv) { this.totalGmv = totalGmv; }
}

