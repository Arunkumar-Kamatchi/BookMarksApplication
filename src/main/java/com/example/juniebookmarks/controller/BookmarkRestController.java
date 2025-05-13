package com.example.juniebookmarks.controller;

import com.example.juniebookmarks.model.dto.BookmarkDTO;
import com.example.juniebookmarks.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@Tag(name = "Bookmark API", description = "Operations for managing bookmarks")
public class BookmarkRestController {

    private final BookmarkService bookmarkService;

    public BookmarkRestController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping
    @Operation(summary = "Get all bookmarks", description = "Retrieves a list of all bookmarks")
    public List<BookmarkDTO> getAllBookmarks() {
        return bookmarkService.getAllBookmarks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bookmark by ID", description = "Retrieves a bookmark by its ID")
    public ResponseEntity<BookmarkDTO> getBookmarkById(
            @Parameter(description = "ID of the bookmark to retrieve") @PathVariable Long id) {
        return bookmarkService.getBookmarkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create bookmark", description = "Creates a new bookmark")
    public BookmarkDTO createBookmark(@RequestBody BookmarkDTO bookmarkDTO) {
        return bookmarkService.createBookmark(bookmarkDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update bookmark", description = "Updates an existing bookmark")
    public ResponseEntity<BookmarkDTO> updateBookmark(
            @Parameter(description = "ID of the bookmark to update") @PathVariable Long id,
            @RequestBody BookmarkDTO bookmarkDTO) {
        return bookmarkService.updateBookmark(id, bookmarkDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete bookmark", description = "Deletes a bookmark")
    public ResponseEntity<Void> deleteBookmark(
            @Parameter(description = "ID of the bookmark to delete") @PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
        return ResponseEntity.noContent().build();
    }
}
