package com.jazeera.api.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String username;

    private Long userId;


    public AuthResponseDto(String accessToken, String username, Long userId) {
        this.accessToken = accessToken;
        this.username = username;
        this.userId = userId; // Set the user ID
    }
}
