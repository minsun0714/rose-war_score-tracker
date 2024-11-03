package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // Post 테이블의 postId를 참조하는 외래 키
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키
    private Player player;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private int likeCount;

    @ManyToOne
    @JoinColumn(name = "parent_id") // 자기 자신을 참조하는 외래 키
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children; // 답글 리스트
}
