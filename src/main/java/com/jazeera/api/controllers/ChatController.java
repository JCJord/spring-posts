package com.jazeera.api.controllers;

import com.jazeera.api.dto.MessageDTO;
import com.jazeera.api.dto.OnlineChatUsersDTO;
import com.jazeera.api.services.MessagesService;
import com.jazeera.api.services.OnlineChatUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/chat")
public class ChatController {
    private MessagesService messagesService;
    private OnlineChatUserService onlineChatUserService;

    public ChatController(MessagesService messagesService, OnlineChatUserService onlineChatUserService) {
        this.messagesService = messagesService;
        this.onlineChatUserService = onlineChatUserService;
    }
    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        List<MessageDTO> messages = messagesService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/onlineUsers")
    public ResponseEntity<List<OnlineChatUsersDTO>> getOnlineUsers() {
        List<OnlineChatUsersDTO> onlineChatUsersDTOS = onlineChatUserService.getOnlineUsers();
        return new ResponseEntity<>(onlineChatUsersDTOS, HttpStatus.OK);
    }
}
