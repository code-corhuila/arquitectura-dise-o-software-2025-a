package com.techshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for TechShop Cloud
 * 
 * This application implements Clean Architecture with Domain-Driven Design
 * principles, providing a robust e-commerce platform.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class TechShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechShopApplication.class, args);
    }
} 