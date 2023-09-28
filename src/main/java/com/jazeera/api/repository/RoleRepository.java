package com.jazeera.api.repository;

import com.jazeera.api.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin("http://localhost:4200")

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
}
