package com.example.juniebookmarks.repository;

import com.example.juniebookmarks.model.entity.BookmarkEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Test
    public void testSaveAndFindById() {
        // Create a new bookmark entity
        BookmarkEntity bookmarkEntity = new BookmarkEntity("Test Bookmark", "https://example.com");

        // Save the bookmark entity
        BookmarkEntity savedEntity = bookmarkRepository.save(bookmarkEntity);

        // Verify the bookmark entity was saved with an ID
        assertThat(savedEntity.getId()).isNotNull();

        // Find the bookmark entity by ID
        Optional<BookmarkEntity> foundEntity = bookmarkRepository.findById(savedEntity.getId());

        // Verify the bookmark entity was found
        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getTitle()).isEqualTo("Test Bookmark");
        assertThat(foundEntity.get().getUrl()).isEqualTo("https://example.com");
        assertThat(foundEntity.get().getCreatedAt()).isNotNull();
    }

    @Test
    public void testFindAll() {
        // Create and save a few bookmark entities
        bookmarkRepository.save(new BookmarkEntity("Google", "https://www.google.com"));
        bookmarkRepository.save(new BookmarkEntity("GitHub", "https://github.com"));

        // Find all bookmark entities
        List<BookmarkEntity> bookmarkEntities = bookmarkRepository.findAll();

        // Verify bookmark entities were found
        assertThat(bookmarkEntities).isNotEmpty();
        assertThat(bookmarkEntities.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testUpdate() {
        // Create and save a bookmark entity
        BookmarkEntity bookmarkEntity = new BookmarkEntity("Original Title", "https://example.com");
        BookmarkEntity savedEntity = bookmarkRepository.save(bookmarkEntity);

        // Update the bookmark entity
        savedEntity.setTitle("Updated Title");
        savedEntity.setUrl("https://updated-example.com");
        BookmarkEntity updatedEntity = bookmarkRepository.save(savedEntity);

        // Find the bookmark entity by ID
        Optional<BookmarkEntity> foundEntity = bookmarkRepository.findById(updatedEntity.getId());

        // Verify the bookmark entity was updated
        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getTitle()).isEqualTo("Updated Title");
        assertThat(foundEntity.get().getUrl()).isEqualTo("https://updated-example.com");
    }

    @Test
    public void testDelete() {
        // Create and save a bookmark entity
        BookmarkEntity bookmarkEntity = new BookmarkEntity("To Be Deleted", "https://example.com");
        BookmarkEntity savedEntity = bookmarkRepository.save(bookmarkEntity);

        // Verify the bookmark entity exists
        assertThat(bookmarkRepository.findById(savedEntity.getId())).isPresent();

        // Delete the bookmark entity
        bookmarkRepository.deleteById(savedEntity.getId());

        // Verify the bookmark entity was deleted
        assertThat(bookmarkRepository.findById(savedEntity.getId())).isEmpty();
    }
}
