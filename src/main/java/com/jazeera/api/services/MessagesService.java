package com.jazeera.api.services;
import java.util.List;
import java.util.stream.Collectors;

import com.jazeera.api.dto.MessageDTO;
import com.jazeera.api.models.Messages;
import com.jazeera.api.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {
    private MessagesRepository messagesRepository;

    @Autowired
    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public Messages createMessage(MessageDTO messageDTO) {
        Messages message = new Messages();
        message.setUser(messageDTO.getUser());
        message.setMessage(messageDTO.getMessage());

        return messagesRepository.save(message);
    }

    public List<MessageDTO> getAllMessages() {
        List<Messages> messages = messagesRepository.findAll();

        List<MessageDTO> messagesDTO = messages.stream().map(MessageDTO::convertToDto).collect(Collectors.toList());
        return messagesDTO;
    }
}
