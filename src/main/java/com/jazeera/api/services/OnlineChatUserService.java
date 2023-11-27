package com.jazeera.api.services;

import com.jazeera.api.dto.MessageDTO;
import com.jazeera.api.dto.OnlineChatUsersDTO;
import com.jazeera.api.dto.PostsDto;
import com.jazeera.api.models.Messages;
import com.jazeera.api.models.OnlineChatUsers;
import com.jazeera.api.models.Posts;
import com.jazeera.api.repository.MessagesRepository;
import com.jazeera.api.repository.OnlineChatUserReposity;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OnlineChatUserService {
    private OnlineChatUserReposity onlineChatUserReposity;

    @Autowired
    public OnlineChatUserService(OnlineChatUserReposity onlineChatUserReposity) {
        this.onlineChatUserReposity = onlineChatUserReposity;
    }

    public void deleteOnlineUser(Long onlineUserId) {
        try {

            onlineChatUserReposity.deleteById(onlineUserId);

        }catch(Exception e) {
            throw new ServiceException("User Not Found", e);
        }
    }

    public OnlineChatUsers createOnlineUser(OnlineChatUsersDTO onlineChatUsersDTO) {
        System.out.println("Online User: "+onlineChatUsersDTO.getUser().getUsername());
        System.out.println("Online User: "+onlineChatUsersDTO.getUser().getId());

        OnlineChatUsers user = onlineChatUserReposity.findByUserId(onlineChatUsersDTO.getUser().getId());
        if(user != null) {
            throw new ServiceException("User Already exist");
        }else {
            OnlineChatUsers onlineUser = new OnlineChatUsers();
            onlineUser.setUser(onlineChatUsersDTO.getUser());
            return onlineChatUserReposity.save(onlineUser);
        }
    }

    public List<OnlineChatUsersDTO> getOnlineUsers() {
        List<OnlineChatUsers> onlineUsers = onlineChatUserReposity.findAll();

        List<OnlineChatUsersDTO> onlineUsersDTO = onlineUsers.stream().map(OnlineChatUsersDTO::convertToDto).collect(Collectors.toList());
        return onlineUsersDTO;
    }

}
