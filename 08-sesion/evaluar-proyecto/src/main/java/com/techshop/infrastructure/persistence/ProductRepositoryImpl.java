package com.techshop.infrastructure.persistence;

import com.techshop.domain.model.Product;
import com.techshop.domain.model.ProductCategory;
import com.techshop.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of ProductRepository using JPA
 * 
 * This class implements the ProductRepository interface using Spring Data JPA,
 * providing the concrete implementation for product persistence operations.
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public List<Product> findAllActive() {
        return productJpaRepository.findByActiveTrue();
    }

    @Override
    public List<Product> findByCategory(ProductCategory category) {
        return productJpaRepository.findByCategory(category);
    }

    @Override
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productJpaRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public List<Product> findByNameContainingIgnoreCase(String name) {
        return productJpaRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> findLowStockProducts(int threshold) {
        return productJpaRepository.findByStockQuantityLessThanEqual(threshold);
    }

    @Override
    public boolean existsByName(String name) {
        return productJpaRepository.existsByName(name);
    }

    @Override
    public void deleteById(UUID id) {
        productJpaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return productJpaRepository.count();
    }

    @Override
    public long countByCategory(ProductCategory category) {
        return productJpaRepository.countByCategory(category);
    }
} 