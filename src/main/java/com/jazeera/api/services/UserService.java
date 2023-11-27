package com.jazeera.api.services;

import com.jazeera.api.dto.PostsDto;
import com.jazeera.api.dto.UserDTO;
import com.jazeera.api.models.UserEntity;
import com.jazeera.api.repository.UserRepository;
import org.apache.catalina.User;
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

    public byte[] getUserAvatarById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        UserEntity user = userOptional.get();
        byte[] avatar = user.getAvatar();
        if(avatar != null){
            return avatar;
        }else {
            return null;
        }
    }

    public String getSignedUsername() {
        Authentication authorizedUser = SecurityContextHolder.getContext().getAuthentication();
        return authorizedUser.getName();
    }

    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity getUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        UserEntity user = new UserEntity();
        user.setId(userEntity.get().getId());
        user.setUsername(userEntity.get().getUsername());
        user.setAvatar(userEntity.get().getAvatar());
        user.setPassword(userEntity.get().getPassword());
        return user;
    }
}