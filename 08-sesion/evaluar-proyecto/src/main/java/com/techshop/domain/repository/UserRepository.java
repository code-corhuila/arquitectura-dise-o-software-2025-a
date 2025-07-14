package com.techshop.domain.repository;

import com.techshop.domain.model.User;
import com.techshop.domain.model.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for User entity
 * 
 * This interface defines the contract for user persistence operations,
 * following the Repository pattern and Clean Architecture principles.
 */
public interface UserRepository {

    /**
     * Save a user
     */
    User save(User user);

    /**
     * Find user by ID
     */
    Optional<User> findById(UUID id);

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Find all users
     */
    List<User> findAll();

    /**
     * Find all active users
     */
    List<User> findAllActive();

    /**
     * Find users by role
     */
    List<User> findByRole(UserRole role);

    /**
     * Find users with locked accounts
     */
    List<User> findLockedAccounts();

    /**
     * Find users with unverified email
     */
    List<User> findUnverifiedEmails();

    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Check if user exists by username
     */
    boolean existsByUsername(String username);

    /**
     * Delete user by ID
     */
    void deleteById(UUID id);

    /**
     * Count total users
     */
    long count();

    /**
     * Count users by role
     */
    long countByRole(UserRole role);
} 