package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Comment {
    @Id
    private String commentId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // Post 테이블의 postId를 참조하는 외래 키
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키
    private User user;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "parent_id") // 자기 자신을 참조하는 외래 키
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> children; // 답글 리스트
}
