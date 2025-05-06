package com.example.bookmarks.repository;

import com.example.bookmarks.model.Bookmark;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository {
    
    /**
     * Find all bookmarks
     * @return List of all bookmarks
     */
    List<Bookmark> findAll();
    
    /**
     * Find a bookmark by its ID
     * @param id the bookmark ID
     * @return Optional containing the bookmark if found
     */
    Optional<Bookmark> findById(Long id);
    
    /**
     * Save a new bookmark
     * @param bookmark the bookmark to save
     * @return the saved bookmark with ID populated
     */
    Bookmark save(Bookmark bookmark);
    
    /**
     * Update an existing bookmark
     * @param bookmark the bookmark to update
     * @return the updated bookmark
     */
    Bookmark update(Bookmark bookmark);
    
    /**
     * Delete a bookmark by its ID
     * @param id the ID of the bookmark to delete
     * @return true if deleted, false if not found
     */
    boolean deleteById(Long id);
}