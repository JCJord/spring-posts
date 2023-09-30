package com.jazeera.api.controllers;

import com.jazeera.api.dto.PostsDto;
import com.jazeera.api.dto.UserDTO;
import com.jazeera.api.repository.UserRepository;
import com.jazeera.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setId(id);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
