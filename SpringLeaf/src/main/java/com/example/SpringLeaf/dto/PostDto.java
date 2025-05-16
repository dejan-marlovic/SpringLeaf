package com.example.SpringLeaf.dto;

public class PostDto {

    private String content;
    private Long userId; // store only the user ID, not the full User object

    // Getters and setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
