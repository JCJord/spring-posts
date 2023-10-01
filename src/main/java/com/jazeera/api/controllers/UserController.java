package com.jazeera.api.controllers;

import com.jazeera.api.dto.PostsDto;
import com.jazeera.api.dto.UserDTO;
import com.jazeera.api.models.Posts;
import com.jazeera.api.models.UserEntity;
import com.jazeera.api.repository.UserRepository;
import com.jazeera.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    private UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/listUser")
    public ResponseEntity<UserDTO> getUserData() {
        String username = userService.getSignedUsername();
        Long id = userService.getUserIdByUsername(username);
        UserEntity user =  userService.getUserById(id);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setId(id);
        userDTO.setAvatar(user.getAvatar());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        try {
            String username = userService.getSignedUsername();
            Long id = userService.getUserIdByUsername(username);

            UserEntity existingUser = userService.getUserById(id);
            existingUser.setAvatar(userDTO.getAvatar());

            userService.updateUser(existingUser);
            return new ResponseEntity<>("User Updated Succesfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
