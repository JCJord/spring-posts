package com.jazeera.api.repository;

import com.jazeera.api.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByCategoryId(Long categoryId);
}
