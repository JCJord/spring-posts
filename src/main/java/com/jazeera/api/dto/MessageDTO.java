package com.jazeera.api.dto;

import com.jazeera.api.models.Messages;
import com.jazeera.api.models.UserEntity;
import lombok.Data;

@Data
public class MessageDTO {

    private Long id;

    private UserEntity user;

    private String message;

    public static MessageDTO convertToDto(Messages messages) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(messages.getId());
        messageDTO.setUser(messages.getUser());
        messageDTO.setMessage(messages.getMessage());

        return messageDTO;
    }
}
