package com.example.juniebookmarks.mapper;

import com.example.juniebookmarks.model.dto.BookmarkDTO;
import com.example.juniebookmarks.model.entity.BookmarkEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class to convert between BookmarkDTO and BookmarkEntity
 */
@Component
public class BookmarkMapper {

    /**
     * Convert a BookmarkEntity to a BookmarkDTO
     * @param entity The BookmarkEntity to convert
     * @return The converted BookmarkDTO
     */
    public BookmarkDTO toDTO(BookmarkEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new BookmarkDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getUrl(),
                entity.getCreatedAt()
        );
    }

    /**
     * Convert a BookmarkDTO to a BookmarkEntity
     * @param dto The BookmarkDTO to convert
     * @return The converted BookmarkEntity
     */
    public BookmarkEntity toEntity(BookmarkDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return new BookmarkEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getUrl(),
                dto.getCreatedAt()
        );
    }

    /**
     * Convert a list of BookmarkEntity objects to a list of BookmarkDTO objects
     * @param entities The list of BookmarkEntity objects to convert
     * @return The list of converted BookmarkDTO objects
     */
    public List<BookmarkDTO> toDTOList(List<BookmarkEntity> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert a list of BookmarkDTO objects to a list of BookmarkEntity objects
     * @param dtos The list of BookmarkDTO objects to convert
     * @return The list of converted BookmarkEntity objects
     */
    public List<BookmarkEntity> toEntityList(List<BookmarkDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}