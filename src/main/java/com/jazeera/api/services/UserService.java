package com.jazeera.api.services;

import com.jazeera.api.models.UserEntity;
import com.jazeera.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    // Your user repository or service for fetching user details
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserIdByUsername(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        return userOptional.map(UserEntity::getId).orElse(null);
    }

    public String getSignedUsername() {
        Authentication authorizedUser = SecurityContextHolder.getContext().getAuthentication();
        return authorizedUser.getName();
    }
}