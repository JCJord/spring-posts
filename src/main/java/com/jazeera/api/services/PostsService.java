package com.jazeera.api.services;

import com.jazeera.api.dto.PostsDto;
import com.jazeera.api.models.Posts;
import com.jazeera.api.repository.PostsRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsService {

    private PostsRepository postsRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public Posts createPost(PostsDto postsDto) {
        Posts post = new Posts();
        post.setUser(postsDto.getUser());
        post.setCategory(postsDto.getCategory());
        post.setTitle(postsDto.getTitle());
        post.setContent(postsDto.getContent());

        return postsRepository.save(post);
    }

    public Posts updatePost(Posts posts) {
        return postsRepository.save(posts);
    }

    public List<PostsDto> getAllPosts() {
        List<Posts> posts = postsRepository.findAll();

        List<PostsDto> postsDtosList = posts.stream()
                .map(PostsDto::convertToDto)
                .collect(Collectors.toList());

        return postsDtosList;
    }

    public List<PostsDto> getPostsByCategoryId(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("Invalid categoryId");
        }

        List<Posts> posts = postsRepository.findByCategoryId(categoryId);
        if(posts.isEmpty()) {
            return Collections.emptyList();
        }else {
            return  posts.stream()
                    .map(PostsDto::convertToDto).collect(Collectors.toList());
        }
    }

    public void deletePostById(Long postId) {
        if(postId == null || postId <= 0) {
            throw new IllegalArgumentException("Invalid Post id");
        }
        try {
            postsRepository.deleteById(postId);
        }catch(Exception e) {
            throw new ServiceException("Post not found", e);
        }
    }

    public Optional<Posts> getPostById(Long postId) {
        // You can add validation here to check if postId is valid
        if (postId == null || postId <= 0) {
            throw new IllegalArgumentException("Invalid postId");
        }

        return postsRepository.findById(postId);
    }
}
