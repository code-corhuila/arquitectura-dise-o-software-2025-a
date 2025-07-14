package com.techshop.domain.repository;

import com.techshop.domain.model.Product;
import com.techshop.domain.model.ProductCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Product entity
 * 
 * This interface defines the contract for product persistence operations,
 * following the Repository pattern and Clean Architecture principles.
 */
public interface ProductRepository {

    /**
     * Save a product
     */
    Product save(Product product);

    /**
     * Find product by ID
     */
    Optional<Product> findById(UUID id);

    /**
     * Find all products
     */
    List<Product> findAll();

    /**
     * Find all active products
     */
    List<Product> findAllActive();

    /**
     * Find products by category
     */
    List<Product> findByCategory(ProductCategory category);

    /**
     * Find products by price range
     */
    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Find products by name (case-insensitive search)
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Find products with low stock (below threshold)
     */
    List<Product> findLowStockProducts(int threshold);

    /**
     * Check if product exists by name
     */
    boolean existsByName(String name);

    /**
     * Delete product by ID
     */
    void deleteById(UUID id);

    /**
     * Count total products
     */
    long count();

    /**
     * Count products by category
     */
    long countByCategory(ProductCategory category);
} 