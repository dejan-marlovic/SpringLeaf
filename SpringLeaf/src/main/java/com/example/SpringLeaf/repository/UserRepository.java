package com.example.SpringLeaf.repository;

import com.example.SpringLeaf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom methods later if needed
}