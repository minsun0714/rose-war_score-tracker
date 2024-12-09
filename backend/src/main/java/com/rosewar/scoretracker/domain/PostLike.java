package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략
    private Long likeId;  // 숫자형 기본 키

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키
    private Player player;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // Post 테이블의 postId를 참조하는 외래 키
    private Post post;
}
