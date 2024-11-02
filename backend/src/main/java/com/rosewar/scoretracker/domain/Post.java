package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Post {
    @Id
    private String postId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키
    private User user;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int likeCount;
}
