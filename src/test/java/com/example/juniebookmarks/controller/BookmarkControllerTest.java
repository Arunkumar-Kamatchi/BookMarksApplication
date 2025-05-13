package com.example.juniebookmarks.controller;

import com.example.juniebookmarks.model.dto.BookmarkDTO;
import com.example.juniebookmarks.service.BookmarkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookmarkService bookmarkService;

    @Test
    public void testHomePage() throws Exception {
        // Create some bookmarks
        bookmarkService.createBookmark(new BookmarkDTO("Controller Test 1", "https://example1.com"));
        bookmarkService.createBookmark(new BookmarkDTO("Controller Test 2", "https://example2.com"));

        // Test the home page
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("bookmarks"))
                .andExpect(content().string(containsString("Controller Test 1")))
                .andExpect(content().string(containsString("Controller Test 2")));
    }

    @Test
    public void testNewBookmarkForm() throws Exception {
        mockMvc.perform(get("/bookmarks/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookmark-form"))
                .andExpect(model().attributeExists("bookmark"));
    }

    @Test
    public void testCreateBookmark() throws Exception {
        mockMvc.perform(post("/bookmarks")
                .param("title", "New Bookmark")
                .param("url", "https://newbookmark.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    public void testEditBookmarkForm() throws Exception {
        // Create a bookmark
        BookmarkDTO bookmark = bookmarkService.createBookmark(new BookmarkDTO("Edit Test", "https://edit-test.com"));

        // Test the edit form
        mockMvc.perform(get("/bookmarks/{id}/edit", bookmark.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("bookmark-form"))
                .andExpect(model().attributeExists("bookmark"))
                .andExpect(model().attribute("bookmark", hasProperty("title", is("Edit Test"))))
                .andExpect(model().attribute("bookmark", hasProperty("url", is("https://edit-test.com"))));
    }

    @Test
    public void testUpdateBookmark() throws Exception {
        // Create a bookmark
        BookmarkDTO bookmark = bookmarkService.createBookmark(new BookmarkDTO("Update Test", "https://update-test.com"));

        // Test updating the bookmark
        mockMvc.perform(post("/bookmarks/{id}", bookmark.getId())
                .param("title", "Updated Bookmark")
                .param("url", "https://updated-bookmark.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("message"));
    }

    @Test
    public void testDeleteBookmark() throws Exception {
        // Create a bookmark
        BookmarkDTO bookmark = bookmarkService.createBookmark(new BookmarkDTO("Delete Test", "https://delete-test.com"));

        // Test deleting the bookmark
        mockMvc.perform(get("/bookmarks/{id}/delete", bookmark.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("message"));
    }
}
