package com.example.juniebookmarks.service;

import com.example.juniebookmarks.mapper.BookmarkMapper;
import com.example.juniebookmarks.model.dto.BookmarkDTO;
import com.example.juniebookmarks.model.entity.BookmarkEntity;
import com.example.juniebookmarks.repository.BookmarkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkMapper bookmarkMapper;

    @Test
    public void testGetAllBookmarks() {
        // Create and save a few bookmarks
        bookmarkRepository.save(new BookmarkEntity("Service Test 1", "https://example1.com"));
        bookmarkRepository.save(new BookmarkEntity("Service Test 2", "https://example2.com"));

        // Get all bookmarks
        List<BookmarkDTO> bookmarks = bookmarkService.getAllBookmarks();

        // Verify bookmarks were found
        assertThat(bookmarks).isNotEmpty();
        assertThat(bookmarks.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testGetBookmarkById() {
        // Create and save a bookmark
        BookmarkEntity bookmarkEntity = new BookmarkEntity("Get By ID Test", "https://example.com");
        BookmarkEntity savedEntity = bookmarkRepository.save(bookmarkEntity);

        // Get the bookmark by ID
        Optional<BookmarkDTO> foundBookmarkDTO = bookmarkService.getBookmarkById(savedEntity.getId());

        // Verify the bookmark was found
        assertThat(foundBookmarkDTO).isPresent();
        assertThat(foundBookmarkDTO.get().getTitle()).isEqualTo("Get By ID Test");
        assertThat(foundBookmarkDTO.get().getUrl()).isEqualTo("https://example.com");
    }

    @Test
    public void testCreateBookmark() {
        // Create a bookmark DTO
        BookmarkDTO bookmarkDTO = new BookmarkDTO("Create Test", "https://example.com");

        // Save the bookmark
        BookmarkDTO createdBookmarkDTO = bookmarkService.createBookmark(bookmarkDTO);

        // Verify the bookmark was created
        assertThat(createdBookmarkDTO.getId()).isNotNull();
        assertThat(createdBookmarkDTO.getTitle()).isEqualTo("Create Test");
        assertThat(createdBookmarkDTO.getUrl()).isEqualTo("https://example.com");
        assertThat(createdBookmarkDTO.getCreatedAt()).isNotNull();
    }

    @Test
    public void testUpdateBookmark() {
        // Create and save a bookmark entity
        BookmarkEntity bookmarkEntity = new BookmarkEntity("Update Test Original", "https://example.com");
        BookmarkEntity savedEntity = bookmarkRepository.save(bookmarkEntity);

        // Create updated bookmark DTO
        BookmarkDTO updatedBookmarkDTO = new BookmarkDTO("Update Test Modified", "https://modified.com");

        // Update the bookmark
        Optional<BookmarkDTO> result = bookmarkService.updateBookmark(savedEntity.getId(), updatedBookmarkDTO);

        // Verify the bookmark was updated
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Update Test Modified");
        assertThat(result.get().getUrl()).isEqualTo("https://modified.com");

        // Verify the update is persisted
        Optional<BookmarkEntity> foundEntity = bookmarkRepository.findById(savedEntity.getId());
        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getTitle()).isEqualTo("Update Test Modified");
        assertThat(foundEntity.get().getUrl()).isEqualTo("https://modified.com");
    }

    @Test
    public void testDeleteBookmark() {
        // Create and save a bookmark entity
        BookmarkEntity bookmarkEntity = new BookmarkEntity("Delete Test", "https://example.com");
        BookmarkEntity savedEntity = bookmarkRepository.save(bookmarkEntity);

        // Verify the bookmark exists
        assertThat(bookmarkRepository.findById(savedEntity.getId())).isPresent();

        // Delete the bookmark
        bookmarkService.deleteBookmark(savedEntity.getId());

        // Verify the bookmark was deleted
        assertThat(bookmarkRepository.findById(savedEntity.getId())).isEmpty();
    }
}
