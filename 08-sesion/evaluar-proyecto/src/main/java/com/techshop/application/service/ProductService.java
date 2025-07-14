package com.techshop.application.service;

import com.techshop.application.dto.CreateProductRequest;
import com.techshop.application.dto.ProductResponse;
import com.techshop.domain.model.Product;
import com.techshop.domain.model.ProductCategory;
import com.techshop.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Application service for product management
 * 
 * This service implements the use cases for product management,
 * orchestrating domain entities and repositories.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Create a new product
     */
    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating product: {}", request.getName());
        
        // Check if product with same name already exists
        if (productRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Product with name '" + request.getName() + "' already exists");
        }

        // Create product entity
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .stockQuantity(request.getStockQuantity())
                .active(true)
                .build();

        // Validate product
        product.validateForCreation();

        // Save product
        Product savedProduct = productRepository.save(product);
        
        log.info("Product created successfully with ID: {}", savedProduct.getId());
        return ProductResponse.fromProduct(savedProduct);
    }

    /**
     * Get product by ID
     */
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        log.info("Fetching product with ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        
        return ProductResponse.fromProduct(product);
    }

    /**
     * Get all products
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all products");
        
        return productRepository.findAll().stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get all active products
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getActiveProducts() {
        log.info("Fetching all active products");
        
        return productRepository.findAllActive().stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get products by category
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(ProductCategory category) {
        log.info("Fetching products by category: {}", category);
        
        return productRepository.findByCategory(category).stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get products by price range
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Fetching products by price range: {} - {}", minPrice, maxPrice);
        
        if (minPrice == null || maxPrice == null || minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("Invalid price range");
        }
        
        return productRepository.findByPriceRange(minPrice, maxPrice).stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Search products by name
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> searchProductsByName(String name) {
        log.info("Searching products by name: {}", name);
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        
        return productRepository.findByNameContainingIgnoreCase(name.trim()).stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Update product price
     */
    public ProductResponse updateProductPrice(UUID id, BigDecimal newPrice) {
        log.info("Updating product price for ID: {} to: {}", id, newPrice);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        
        product.updatePrice(newPrice);
        Product updatedProduct = productRepository.save(product);
        
        log.info("Product price updated successfully");
        return ProductResponse.fromProduct(updatedProduct);
    }

    /**
     * Update product stock
     */
    public ProductResponse updateProductStock(UUID id, Integer newStockQuantity) {
        log.info("Updating product stock for ID: {} to: {}", id, newStockQuantity);
        
        if (newStockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        
        product.setStockQuantity(newStockQuantity);
        Product updatedProduct = productRepository.save(product);
        
        log.info("Product stock updated successfully");
        return ProductResponse.fromProduct(updatedProduct);
    }

    /**
     * Reserve product stock
     */
    public void reserveProductStock(UUID id, Integer quantity) {
        log.info("Reserving stock for product ID: {}, quantity: {}", id, quantity);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        
        product.reserveStock(quantity);
        productRepository.save(product);
        
        log.info("Product stock reserved successfully");
    }

    /**
     * Release product stock
     */
    public void releaseProductStock(UUID id, Integer quantity) {
        log.info("Releasing stock for product ID: {}, quantity: {}", id, quantity);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        
        product.releaseStock(quantity);
        productRepository.save(product);
        
        log.info("Product stock released successfully");
    }

    /**
     * Deactivate product
     */
    public ProductResponse deactivateProduct(UUID id) {
        log.info("Deactivating product with ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        
        product.deactivate();
        Product updatedProduct = productRepository.save(product);
        
        log.info("Product deactivated successfully");
        return ProductResponse.fromProduct(updatedProduct);
    }

    /**
     * Activate product
     */
    public ProductResponse activateProduct(UUID id) {
        log.info("Activating product with ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        
        product.activate();
        Product updatedProduct = productRepository.save(product);
        
        log.info("Product activated successfully");
        return ProductResponse.fromProduct(updatedProduct);
    }

    /**
     * Delete product
     */
    public void deleteProduct(UUID id) {
        log.info("Deleting product with ID: {}", id);
        
        if (!productRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        
        productRepository.deleteById(id);
        log.info("Product deleted successfully");
    }

    /**
     * Get low stock products
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> getLowStockProducts(int threshold) {
        log.info("Fetching products with low stock (threshold: {})", threshold);
        
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative");
        }
        
        return productRepository.findLowStockProducts(threshold).stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
    }

    /**
     * Get product statistics
     */
    @Transactional(readOnly = true)
    public ProductStatistics getProductStatistics() {
        log.info("Fetching product statistics");
        
        long totalProducts = productRepository.count();
        long activeProducts = productRepository.findAllActive().size();
        
        return ProductStatistics.builder()
                .totalProducts(totalProducts)
                .activeProducts(activeProducts)
                .inactiveProducts(totalProducts - activeProducts)
                .build();
    }

    /**
     * Inner class for product statistics
     */
    @lombok.Data
    @lombok.Builder
    public static class ProductStatistics {
        private long totalProducts;
        private long activeProducts;
        private long inactiveProducts;
    }
} 