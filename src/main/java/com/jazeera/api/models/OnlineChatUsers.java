package com.jazeera.api.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "online_users")
@Data
@NoArgsConstructor
public class OnlineChatUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
