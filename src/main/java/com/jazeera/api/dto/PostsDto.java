package com.jazeera.api.dto;

import com.jazeera.api.models.Category;
import com.jazeera.api.models.Posts;
import com.jazeera.api.models.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostsDto {
    private Long id;
    private String title;
    private byte[] content;
    private Category category;
    private UserEntity user;
    private LocalDateTime createdAt;

    public static PostsDto convertToDto(Posts posts) {
        PostsDto postsDto = new PostsDto();
        postsDto.setId(posts.getId());
        postsDto.setUser(posts.getUser());
        postsDto.setTitle(posts.getTitle());
        postsDto.setContent(posts.getContent());
        postsDto.setCategory(posts.getCategory());
        postsDto.setCreatedAt(posts.getCreatedAt());
        return postsDto;
    }
}
