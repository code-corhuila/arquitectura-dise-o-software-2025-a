package com.techshop.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * User entity representing a user in the e-commerce system
 * 
 * This entity encapsulates all business rules and validations related to users,
 * including authentication, authorization, and user management.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;

    @Column(name = "account_locked")
    private Boolean accountLocked = false;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Business method to check if user is active
     */
    public boolean isActive() {
        return active && !accountLocked;
    }

    /**
     * Business method to check if user has admin role
     */
    public boolean isAdmin() {
        return UserRole.ADMIN.equals(role);
    }

    /**
     * Business method to check if user has provider role
     */
    public boolean isProvider() {
        return UserRole.PROVIDER.equals(role);
    }

    /**
     * Business method to check if user has user role
     */
    public boolean isRegularUser() {
        return UserRole.USER.equals(role);
    }

    /**
     * Business method to increment failed login attempts
     */
    public void incrementFailedLoginAttempts() {
        this.failedLoginAttempts++;
        if (this.failedLoginAttempts >= 5) {
            this.accountLocked = true;
        }
    }

    /**
     * Business method to reset failed login attempts
     */
    public void resetFailedLoginAttempts() {
        this.failedLoginAttempts = 0;
        this.accountLocked = false;
    }

    /**
     * Business method to unlock account
     */
    public void unlockAccount() {
        this.accountLocked = false;
        this.failedLoginAttempts = 0;
    }

    /**
     * Business method to deactivate user
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Business method to activate user
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Business method to verify email
     */
    public void verifyEmail() {
        this.emailVerified = true;
    }

    /**
     * Business method to update last login
     */
    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    /**
     * Business method to get full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Validation method for user creation
     */
    public void validateForCreation() {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (role == null) {
            throw new IllegalArgumentException("User role is required");
        }
    }

    /**
     * Simple email validation
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
} 