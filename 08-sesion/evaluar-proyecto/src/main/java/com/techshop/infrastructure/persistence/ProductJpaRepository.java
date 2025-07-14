package com.techshop.infrastructure.persistence;

import com.techshop.domain.model.Product;
import com.techshop.domain.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * JPA Repository for Product entity
 * 
 * This repository extends JpaRepository to provide basic CRUD operations
 * and custom query methods for product persistence.
 */
@Repository
public interface ProductJpaRepository extends JpaRepository<Product, UUID> {

    /**
     * Find all active products
     */
    List<Product> findByActiveTrue();

    /**
     * Find products by category
     */
    List<Product> findByCategory(ProductCategory category);

    /**
     * Find products by price range
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceBetween(@Param("minPrice") BigDecimal minPrice, 
                                   @Param("maxPrice") BigDecimal maxPrice);

    /**
     * Find products by name containing (case-insensitive)
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Find products with low stock
     */
    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= :threshold")
    List<Product> findByStockQuantityLessThanEqual(@Param("threshold") int threshold);

    /**
     * Check if product exists by name
     */
    boolean existsByName(String name);

    /**
     * Count products by category
     */
    long countByCategory(ProductCategory category);
} 