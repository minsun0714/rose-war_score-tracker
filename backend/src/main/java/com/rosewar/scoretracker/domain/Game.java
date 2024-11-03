package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 전략
    private Long gameId;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키
    private User player1Id;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false) // User 테이블의 userId를 참조하는 외래 키
    private User player2Id;

    private int score1;

    private int score2;
}
