package com.jazeera.api.controllers;

import com.jazeera.api.dto.MessageDTO;
import com.jazeera.api.dto.OnlineChatUsersDTO;
import com.jazeera.api.models.*;
import com.jazeera.api.services.MessagesService;
import com.jazeera.api.services.OnlineChatUserService;
import com.jazeera.api.services.PostsService;
import com.jazeera.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.awt.*;

@Controller
public class WebSocketChatController {

    private MessagesService messagesService;
    private UserService userService;
    private OnlineChatUserService onlineChatUserService;
    @Autowired
    public WebSocketChatController(MessagesService messagesService,
                                   UserService userService,
                                   OnlineChatUserService onlineChatUserService

    ) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.onlineChatUserService = onlineChatUserService;
    }
    @MessageMapping("/chat/send")
    @SendTo("/topic/global-chat")
    public Messages sendMessage(MessageDTO messageDTO) {
        // Handle the received message, e.g., save to the database
        byte[] avatar = userService.getUserAvatarById(messageDTO.getUser().getId());
        UserEntity user = new UserEntity();
        user.setAvatar(avatar);
        user.setId(messageDTO.getUser().getId());
        user.setUsername(messageDTO.getUser().getUsername());

        messageDTO.setUser(user);
        System.out.println("authenticated user avatar" + avatar);
        Messages savedMessage = messagesService.createMessage(messageDTO);
        return savedMessage;
    }

    @MessageMapping("/chat/save-user")
    @SendTo("/topic/chat-status")
    public OnlineChatUsers notifyEnteredUser(OnlineChatUsersDTO onlineChatUsersDTO) {
        byte[] avatar = userService.getUserAvatarById(onlineChatUsersDTO.getUser().getId());
        UserEntity user = new UserEntity();
        user.setAvatar(avatar);
        user.setId(onlineChatUsersDTO.getUser().getId());
        user.setUsername(onlineChatUsersDTO.getUser().getUsername());

        onlineChatUsersDTO.setUser(user);

        OnlineChatUsers savedOnlineUser = onlineChatUserService.createOnlineUser(onlineChatUsersDTO);
        return savedOnlineUser;
    }

    @MessageMapping("/chat/remove-user")
    @SendTo("/topic/chat-leave")
    public OnlineChatUsers removeOnlineUser(OnlineChatUsersDTO onlineChatUsersDTO) {
        onlineChatUserService.deleteOnlineUser(onlineChatUsersDTO.getId());
        OnlineChatUsers onlineChatUsers = new OnlineChatUsers();
        onlineChatUsers.setId(onlineChatUsersDTO.getId());

        return onlineChatUsers;
    }
}
