package com.example.bookmarks.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bookmark {
    private Long id;
    private String title;
    private String url;
    private LocalDateTime createdAt;

    // Default constructor
    public Bookmark() {
    }

    // Constructor with fields
    public Bookmark(Long id, String title, String url, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.createdAt = createdAt;
    }

    // Constructor without id for new bookmarks
    public Bookmark(String title, String url) {
        this.title = title;
        this.url = url;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return Objects.equals(id, bookmark.id) &&
               Objects.equals(title, bookmark.title) &&
               Objects.equals(url, bookmark.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, url);
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}