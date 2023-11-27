package com.jazeera.api.repository;

import com.jazeera.api.models.OnlineChatUsers;
import com.jazeera.api.models.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineChatUserReposity extends JpaRepository<OnlineChatUsers, Long> {
    OnlineChatUsers findByUserId(Long userId);
}
