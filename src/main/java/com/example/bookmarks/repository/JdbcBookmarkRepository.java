package com.example.bookmarks.repository;

import com.example.bookmarks.model.Bookmark;
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
public class JdbcBookmarkRepository implements BookmarkRepository {

    private final JdbcTemplate jdbcTemplate;

    // Row mapper for converting result set rows to Bookmark objects
    private final RowMapper<Bookmark> bookmarkRowMapper = (rs, rowNum) -> {
        Bookmark bookmark = new Bookmark();
        bookmark.setId(rs.getLong("id"));
        bookmark.setTitle(rs.getString("title"));
        bookmark.setUrl(rs.getString("url"));
        Timestamp timestamp = rs.getTimestamp("created_at");
        bookmark.setCreatedAt(timestamp != null ? timestamp.toLocalDateTime() : null);
        return bookmark;
    };

    public JdbcBookmarkRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Bookmark> findAll() {
        String sql = "SELECT id, title, url, created_at FROM bookmarks ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, bookmarkRowMapper);
    }

    @Override
    public Optional<Bookmark> findById(Long id) {
        String sql = "SELECT id, title, url, created_at FROM bookmarks WHERE id = ?";
        List<Bookmark> results = jdbcTemplate.query(sql, bookmarkRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Bookmark save(Bookmark bookmark) {
        String sql = "INSERT INTO bookmarks (title, url, created_at) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        LocalDateTime now = LocalDateTime.now();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, bookmark.getTitle());
            ps.setString(2, bookmark.getUrl());
            ps.setTimestamp(3, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);
        
        bookmark.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        bookmark.setCreatedAt(now);
        
        return bookmark;
    }

    @Override
    public Bookmark update(Bookmark bookmark) {
        String sql = "UPDATE bookmarks SET title = ?, url = ? WHERE id = ?";
        int updated = jdbcTemplate.update(sql, bookmark.getTitle(), bookmark.getUrl(), bookmark.getId());
        
        if (updated == 0) {
            throw new RuntimeException("Bookmark not found with id: " + bookmark.getId());
        }
        
        // Refresh the bookmark from the database to get the current created_at value
        return findById(bookmark.getId()).orElseThrow(() -> 
            new RuntimeException("Bookmark not found after update with id: " + bookmark.getId()));
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM bookmarks WHERE id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        return deleted > 0;
    }
}