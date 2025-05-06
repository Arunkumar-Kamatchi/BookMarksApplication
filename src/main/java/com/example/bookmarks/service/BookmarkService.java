package com.example.bookmarks.service;

import com.example.bookmarks.model.Bookmark;
import com.example.bookmarks.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    /**
     * Get all bookmarks
     * @return List of all bookmarks
     */
    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    /**
     * Get a bookmark by its ID
     * @param id the bookmark ID
     * @return Optional containing the bookmark if found
     */
    public Optional<Bookmark> getBookmarkById(Long id) {
        return bookmarkRepository.findById(id);
    }

    /**
     * Create a new bookmark
     * @param bookmark the bookmark to create
     * @return the created bookmark with ID populated
     */
    public Bookmark createBookmark(Bookmark bookmark) {
        // Validate bookmark
        validateBookmark(bookmark);
        return bookmarkRepository.save(bookmark);
    }

    /**
     * Update an existing bookmark
     * @param id the ID of the bookmark to update
     * @param bookmark the updated bookmark data
     * @return the updated bookmark
     * @throws RuntimeException if the bookmark is not found
     */
    public Bookmark updateBookmark(Long id, Bookmark bookmark) {
        // Validate bookmark
        validateBookmark(bookmark);
        
        // Ensure the ID is set correctly
        bookmark.setId(id);
        
        return bookmarkRepository.update(bookmark);
    }

    /**
     * Delete a bookmark by its ID
     * @param id the ID of the bookmark to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteBookmark(Long id) {
        return bookmarkRepository.deleteById(id);
    }
    
    /**
     * Validate bookmark data
     * @param bookmark the bookmark to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateBookmark(Bookmark bookmark) {
        if (bookmark.getTitle() == null || bookmark.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Bookmark title cannot be empty");
        }
        
        if (bookmark.getUrl() == null || bookmark.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("Bookmark URL cannot be empty");
        }
        
        // Basic URL validation - could be enhanced with regex pattern
        if (!bookmark.getUrl().startsWith("http://") && !bookmark.getUrl().startsWith("https://")) {
            bookmark.setUrl("https://" + bookmark.getUrl());
        }
    }
}