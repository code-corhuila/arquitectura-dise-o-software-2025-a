package com.techshop.domain.model;

/**
 * Product category enumeration representing different product types
 * 
 * This is a Value Object that encapsulates the business concept of product categories
 * in the e-commerce domain.
 */
public enum ProductCategory {
    
    ELECTRONICS("Electronics"),
    COMPUTERS("Computers"),
    MOBILE_PHONES("Mobile Phones"),
    ACCESSORIES("Accessories"),
    GAMING("Gaming"),
    SOFTWARE("Software"),
    BOOKS("Books"),
    CLOTHING("Clothing"),
    HOME_AND_GARDEN("Home & Garden"),
    SPORTS("Sports"),
    TOYS("Toys"),
    HEALTH("Health & Beauty"),
    AUTOMOTIVE("Automotive"),
    OTHER("Other");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
} 