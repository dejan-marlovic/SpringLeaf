package com.example.SpringLeaf.repository;

import com.example.SpringLeaf.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Optional: custom query methods like findByUserId, etc.
}