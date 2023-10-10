package com.jazeera.api.repository;

import com.jazeera.api.models.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Posts> findAllByOrderByCreatedAtDesc(Pageable pageable);
//
    Page<Posts> findByCategoryIdOrderByCreatedAtDesc(Long categoryId, Pageable pageable);

}
