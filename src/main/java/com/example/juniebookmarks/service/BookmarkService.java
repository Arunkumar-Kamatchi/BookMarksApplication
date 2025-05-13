package com.example.juniebookmarks.service;

import com.example.juniebookmarks.mapper.BookmarkMapper;
import com.example.juniebookmarks.model.dto.BookmarkDTO;
import com.example.juniebookmarks.model.entity.BookmarkEntity;
import com.example.juniebookmarks.repository.BookmarkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkMapper bookmarkMapper;

    public BookmarkService(BookmarkRepository bookmarkRepository, BookmarkMapper bookmarkMapper) {
        this.bookmarkRepository = bookmarkRepository;
        this.bookmarkMapper = bookmarkMapper;
    }

    /**
     * Get all bookmarks
     * @return List of all bookmarks as DTOs
     */
    public List<BookmarkDTO> getAllBookmarks() {
        List<BookmarkEntity> entities = bookmarkRepository.findAll();
        return bookmarkMapper.toDTOList(entities);
    }

    /**
     * Get a bookmark by ID
     * @param id The ID of the bookmark to retrieve
     * @return The bookmark DTO if found, otherwise empty
     */
    public Optional<BookmarkDTO> getBookmarkById(Long id) {
        return bookmarkRepository.findById(id)
                .map(bookmarkMapper::toDTO);
    }

    /**
     * Create a new bookmark
     * @param bookmarkDTO The bookmark DTO to create
     * @return The created bookmark DTO with ID and createdAt set
     */
    public BookmarkDTO createBookmark(BookmarkDTO bookmarkDTO) {
        BookmarkEntity entity = bookmarkMapper.toEntity(bookmarkDTO);
        BookmarkEntity savedEntity = bookmarkRepository.save(entity);
        return bookmarkMapper.toDTO(savedEntity);
    }

    /**
     * Update an existing bookmark
     * @param id The ID of the bookmark to update
     * @param bookmarkDTO The updated bookmark DTO data
     * @return The updated bookmark DTO if found, otherwise empty
     */
    public Optional<BookmarkDTO> updateBookmark(Long id, BookmarkDTO bookmarkDTO) {
        return bookmarkRepository.findById(id)
                .map(existingEntity -> {
                    BookmarkEntity entityToUpdate = bookmarkMapper.toEntity(bookmarkDTO);
                    entityToUpdate.setId(id);
                    BookmarkEntity updatedEntity = bookmarkRepository.save(entityToUpdate);
                    return bookmarkMapper.toDTO(updatedEntity);
                });
    }

    /**
     * Delete a bookmark by ID
     * @param id The ID of the bookmark to delete
     */
    public void deleteBookmark(Long id) {
        bookmarkRepository.deleteById(id);
    }
}
