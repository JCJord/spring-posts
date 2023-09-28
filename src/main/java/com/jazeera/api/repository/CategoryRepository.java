package com.jazeera.api.repository;

import com.jazeera.api.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin("http://localhost:4200")
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
