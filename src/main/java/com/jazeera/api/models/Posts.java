package com.jazeera.api.models;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=100000)
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @ManyToOne // Many posts belong to one category
    @JoinColumn(name = "category_id") // Create a foreign key column in the posts table
    private Category category;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
