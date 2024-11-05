package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략
    private Long gameId;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키.
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키.
    private Player player2;

    private int score1;

    private int score2;

    @CreationTimestamp
    private LocalDateTime playedAt;
}
