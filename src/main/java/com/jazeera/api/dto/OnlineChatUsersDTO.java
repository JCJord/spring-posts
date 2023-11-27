package com.jazeera.api.dto;

import com.jazeera.api.models.Messages;
import com.jazeera.api.models.OnlineChatUsers;
import com.jazeera.api.models.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlineChatUsersDTO {
    private Long id;

    private UserEntity user;

    public static OnlineChatUsersDTO convertToDto(OnlineChatUsers onlineChatUsers) {
        OnlineChatUsersDTO onlineChatUsersDTO = new OnlineChatUsersDTO();
        onlineChatUsersDTO.setId(onlineChatUsers.getId());
        onlineChatUsersDTO.setUser(onlineChatUsers.getUser());
        return onlineChatUsersDTO;
    }
}
