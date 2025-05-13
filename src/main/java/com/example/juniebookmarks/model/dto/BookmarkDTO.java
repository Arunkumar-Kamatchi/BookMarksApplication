package com.example.juniebookmarks.model.dto;

import java.time.LocalDateTime;

/**
 * DTO class for Bookmark used in the controller layer for request/response handling
 */
public class BookmarkDTO {
    private Long id;
    private String title;
    private String url;
    private LocalDateTime createdAt;

    // Default constructor
    public BookmarkDTO() {
    }

    // Constructor with fields
    public BookmarkDTO(String title, String url) {
        this.title = title;
        this.url = url;
    }

    // Constructor with all fields
    public BookmarkDTO(Long id, String title, String url, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "BookmarkDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}