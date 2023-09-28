package com.jazeera.api.controllers;

import com.jazeera.api.dto.PostsDto;
import com.jazeera.api.models.Posts;
import com.jazeera.api.models.UserEntity;
import com.jazeera.api.repository.PostsRepository;
import com.jazeera.api.services.PostsService;
import com.jazeera.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/posts")
public class PostsController {
    private PostsService postsService;

    private UserService userService;
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    public PostsController(PostsService postsService, UserService userService) {
        this.postsService = postsService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Posts> createPost(@RequestBody PostsDto postsDto) {

        String username = userService.getSignedUsername();
        Long userId = userService.getUserIdByUsername(username);
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername(username);
        postsDto.setUser(user);

        Posts savedPost = postsService.createPost(postsDto);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/listAllPosts")
    public ResponseEntity<List<PostsDto>> getAllPosts() {
        List<PostsDto> postsDtosList = postsService.getAllPosts();
        return new ResponseEntity<>(postsDtosList, HttpStatus.OK);
    }

    @GetMapping("/listByCategoryId/{categoryId}")
    public ResponseEntity<List<PostsDto>> getPostsByCategoryId(@PathVariable Long categoryId) {
        List<PostsDto> postsDtos = postsService.getPostsByCategoryId(categoryId);
        if (!postsDtos.isEmpty()) {
            return new ResponseEntity<>(postsDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{postId}")
        public ResponseEntity<?> deletePost(@PathVariable Long postId) {
            try {
                Optional<Posts> post = postsService.getPostById(postId);
                String signedUser = userService.getSignedUsername();

                if(post.get().getUser().getUsername().equals(signedUser)) {
                    postsService.deletePostById(postId);
                    return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("The current user do not own this post", HttpStatus.UNAUTHORIZED);
                }
            // Check if the post exists
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting post: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{postId}")
    public ResponseEntity<PostsDto> getPostById(@PathVariable Long postId) {
        try {
            // Attempt to find the post by its ID
            Optional<Posts> post = postsService.getPostById(postId);

            if (post.isPresent()) {
                PostsDto postsDto = PostsDto.convertToDto(post.get());

                return new ResponseEntity<>(postsDto, HttpStatus.OK);
            } else {
                // If the post is not found, return a 404 Not Found response
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle any potential exceptions, and return a 500 Internal Server Error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<Posts> updatePost(@PathVariable Long postId, @RequestBody PostsDto updatedPostDto) {
        try {
            // Check if the post exists
            Optional<Posts> existingPost = postsService.getPostById(postId);
            String signedUser = userService.getSignedUsername();

            if (existingPost.isPresent()) {

                if (existingPost.get().getUser().getUsername().equals(signedUser)) {
                    System.out.println(updatedPostDto.getContent());
                    Posts postToUpdate = existingPost.get();
                    postToUpdate.setTitle(updatedPostDto.getTitle());
                    postToUpdate.setContent(updatedPostDto.getContent());
                    postToUpdate.setCategory(updatedPostDto.getCategory());

                    Posts savedPost = postsService.updatePost(postToUpdate);

                    return new ResponseEntity<>(savedPost, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                // If the post is not found, return a 404 Not Found response
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle any potential exceptions, and return a 500 Internal Server Error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
