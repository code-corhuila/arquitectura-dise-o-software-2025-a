package com.techshop.interfaces.rest;

import com.techshop.application.dto.CreateProductRequest;
import com.techshop.application.dto.ProductResponse;
import com.techshop.application.service.ProductService;
import com.techshop.domain.model.ProductCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Product management
 * 
 * This controller exposes REST endpoints for product operations,
 * following RESTful conventions and providing comprehensive API documentation.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Products", description = "Product management operations")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        log.info("Creating product: {}", request.getName());
        ProductResponse product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique identifier")
    public ResponseEntity<ProductResponse> getProduct(
            @Parameter(description = "Product ID") @PathVariable UUID id) {
        log.info("Fetching product with ID: {}", id);
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves all products in the system")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("Fetching all products");
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active products", description = "Retrieves all active products")
    public ResponseEntity<List<ProductResponse>> getActiveProducts() {
        log.info("Fetching active products");
        List<ProductResponse> products = productService.getActiveProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "Retrieves products filtered by category")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @Parameter(description = "Product category") @PathVariable ProductCategory category) {
        log.info("Fetching products by category: {}", category);
        List<ProductResponse> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name", description = "Searches products by name (case-insensitive)")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @Parameter(description = "Search term") @RequestParam String name) {
        log.info("Searching products by name: {}", name);
        List<ProductResponse> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get products by price range", description = "Retrieves products within a price range")
    public ResponseEntity<List<ProductResponse>> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice) {
        log.info("Fetching products by price range: {} - {}", minPrice, maxPrice);
        List<ProductResponse> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}/price")
    @Operation(summary = "Update product price", description = "Updates the price of a specific product")
    public ResponseEntity<ProductResponse> updateProductPrice(
            @Parameter(description = "Product ID") @PathVariable UUID id,
            @Parameter(description = "New price") @RequestParam BigDecimal price) {
        log.info("Updating product price for ID: {} to: {}", id, price);
        ProductResponse product = productService.updateProductPrice(id, price);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Updates the stock quantity of a specific product")
    public ResponseEntity<ProductResponse> updateProductStock(
            @Parameter(description = "Product ID") @PathVariable UUID id,
            @Parameter(description = "New stock quantity") @RequestParam Integer stockQuantity) {
        log.info("Updating product stock for ID: {} to: {}", id, stockQuantity);
        ProductResponse product = productService.updateProductStock(id, stockQuantity);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}/reserve-stock")
    @Operation(summary = "Reserve product stock", description = "Reserves stock for a specific product")
    public ResponseEntity<Void> reserveProductStock(
            @Parameter(description = "Product ID") @PathVariable UUID id,
            @Parameter(description = "Quantity to reserve") @RequestParam Integer quantity) {
        log.info("Reserving stock for product ID: {}, quantity: {}", id, quantity);
        productService.reserveProductStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/release-stock")
    @Operation(summary = "Release product stock", description = "Releases reserved stock for a specific product")
    public ResponseEntity<Void> releaseProductStock(
            @Parameter(description = "Product ID") @PathVariable UUID id,
            @Parameter(description = "Quantity to release") @RequestParam Integer quantity) {
        log.info("Releasing stock for product ID: {}, quantity: {}", id, quantity);
        productService.releaseProductStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate product", description = "Activates a deactivated product")
    public ResponseEntity<ProductResponse> activateProduct(
            @Parameter(description = "Product ID") @PathVariable UUID id) {
        log.info("Activating product with ID: {}", id);
        ProductResponse product = productService.activateProduct(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate product", description = "Deactivates an active product")
    public ResponseEntity<ProductResponse> deactivateProduct(
            @Parameter(description = "Product ID") @PathVariable UUID id) {
        log.info("Deactivating product with ID: {}", id);
        ProductResponse product = productService.deactivateProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock products", description = "Retrieves products with stock below threshold")
    public ResponseEntity<List<ProductResponse>> getLowStockProducts(
            @Parameter(description = "Stock threshold") @RequestParam(defaultValue = "10") int threshold) {
        log.info("Fetching products with low stock (threshold: {})", threshold);
        List<ProductResponse> products = productService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get product statistics", description = "Retrieves statistics about products")
    public ResponseEntity<ProductService.ProductStatistics> getProductStatistics() {
        log.info("Fetching product statistics");
        ProductService.ProductStatistics statistics = productService.getProductStatistics();
        return ResponseEntity.ok(statistics);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product by its ID")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID") @PathVariable UUID id) {
        log.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
} 