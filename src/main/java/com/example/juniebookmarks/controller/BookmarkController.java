package com.example.juniebookmarks.controller;

import com.example.juniebookmarks.model.dto.BookmarkDTO;
import com.example.juniebookmarks.service.BookmarkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    /**
     * Display the home page with all bookmarks
     */
    @GetMapping
    public String home(Model model) {
        model.addAttribute("bookmarks", bookmarkService.getAllBookmarks());
        return "home";
    }

    /**
     * Display the form for creating a new bookmark
     */
    @GetMapping("/bookmarks/new")
    public String newBookmarkForm(Model model) {
        model.addAttribute("bookmark", new BookmarkDTO());
        return "bookmark-form";
    }

    /**
     * Handle the submission of a new bookmark
     */
    @PostMapping("/bookmarks")
    public String createBookmark(@ModelAttribute BookmarkDTO bookmarkDTO, RedirectAttributes redirectAttributes) {
        bookmarkService.createBookmark(bookmarkDTO);
        redirectAttributes.addFlashAttribute("message", "Bookmark created successfully!");
        return "redirect:/";
    }

    /**
     * Display the form for editing an existing bookmark
     */
    @GetMapping("/bookmarks/{id}/edit")
    public String editBookmarkForm(@PathVariable Long id, Model model) {
        bookmarkService.getBookmarkById(id).ifPresent(bookmark -> {
            model.addAttribute("bookmark", bookmark);
        });
        return "bookmark-form";
    }

    /**
     * Handle the submission of an edited bookmark
     */
    @PostMapping("/bookmarks/{id}")
    public String updateBookmark(@PathVariable Long id, @ModelAttribute BookmarkDTO bookmarkDTO, RedirectAttributes redirectAttributes) {
        bookmarkService.updateBookmark(id, bookmarkDTO);
        redirectAttributes.addFlashAttribute("message", "Bookmark updated successfully!");
        return "redirect:/";
    }

    /**
     * Handle the deletion of a bookmark
     */
    @GetMapping("/bookmarks/{id}/delete")
    public String deleteBookmark(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookmarkService.deleteBookmark(id);
        redirectAttributes.addFlashAttribute("message", "Bookmark deleted successfully!");
        return "redirect:/";
    }
}
