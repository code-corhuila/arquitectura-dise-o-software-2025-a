package com.techshop.domain.model;

/**
 * User role enumeration representing different user types and permissions
 * 
 * This is a Value Object that encapsulates the business concept of user roles
 * in the e-commerce domain, implementing Role-Based Access Control (RBAC).
 */
public enum UserRole {
    
    USER("User", "Regular user with basic permissions"),
    ADMIN("Administrator", "System administrator with full permissions"),
    PROVIDER("Provider", "Product provider with product management permissions");

    private final String displayName;
    private final String description;

    UserRole(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
} 