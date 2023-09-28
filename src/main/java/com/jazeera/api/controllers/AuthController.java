package com.jazeera.api.controllers;

import com.jazeera.api.dto.AuthResponseDto;
import com.jazeera.api.dto.LoginDto;
import com.jazeera.api.dto.RegisterDto;
import com.jazeera.api.models.Roles;
import com.jazeera.api.models.UserEntity;
import com.jazeera.api.repository.RoleRepository;
import com.jazeera.api.repository.UserRepository;
import com.jazeera.api.security.CustomUserDetailsService;
import com.jazeera.api.security.JwtGenerator;
import com.jazeera.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    private UserService userService;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        String username = authentication.getName();
        Long userId = userService.getUserIdByUsername(username);

        return new ResponseEntity<>(new AuthResponseDto(token, username, userId), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register (@RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Roles roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }
}
