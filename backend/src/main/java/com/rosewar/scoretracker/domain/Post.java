package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략
    private Long postId;  // 숫자형 기본 키

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키
    private User user;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int likeCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments; // 댓글 리스트
}
