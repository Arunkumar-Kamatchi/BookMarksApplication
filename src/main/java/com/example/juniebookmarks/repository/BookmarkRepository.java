package com.example.juniebookmarks.repository;

import com.example.juniebookmarks.model.entity.BookmarkEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class BookmarkRepository {

    private final JdbcTemplate jdbcTemplate;

    // Row mapper for converting result set rows to BookmarkEntity objects
    private final RowMapper<BookmarkEntity> bookmarkRowMapper = (rs, rowNum) -> {
        BookmarkEntity bookmark = new BookmarkEntity();
        bookmark.setId(rs.getLong("id"));
        bookmark.setTitle(rs.getString("title"));
        bookmark.setUrl(rs.getString("url"));
        Timestamp timestamp = rs.getTimestamp("created_at");
        bookmark.setCreatedAt(timestamp != null ? timestamp.toLocalDateTime() : null);
        return bookmark;
    };

    public BookmarkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Find all bookmarks
    public List<BookmarkEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM bookmarks ORDER BY created_at DESC", bookmarkRowMapper);
    }

    // Find bookmark by ID
    public Optional<BookmarkEntity> findById(Long id) {
        try {
            BookmarkEntity bookmark = jdbcTemplate.queryForObject(
                    "SELECT * FROM bookmarks WHERE id = ?",
                    bookmarkRowMapper,
                    id
            );
            return Optional.ofNullable(bookmark);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Save a new bookmark
    public BookmarkEntity save(BookmarkEntity bookmark) {
        if (bookmark.getId() == null) {
            return insert(bookmark);
        } else {
            return update(bookmark);
        }
    }

    // Insert a new bookmark
    private BookmarkEntity insert(BookmarkEntity bookmark) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO bookmarks (title, url) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, bookmark.getTitle());
            ps.setString(2, bookmark.getUrl());
            return ps;
        }, keyHolder);

        // Extract the ID from the map of generated keys
        Long id = ((Number) keyHolder.getKeys().get("ID")).longValue();
        bookmark.setId(id);

        // Fetch the created bookmark to get the created_at timestamp
        return findById(id).orElse(bookmark);
    }

    // Update an existing bookmark
    private BookmarkEntity update(BookmarkEntity bookmark) {
        jdbcTemplate.update(
                "UPDATE bookmarks SET title = ?, url = ? WHERE id = ?",
                bookmark.getTitle(),
                bookmark.getUrl(),
                bookmark.getId()
        );
        return bookmark;
    }

    // Delete a bookmark by ID
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM bookmarks WHERE id = ?", id);
    }
}
