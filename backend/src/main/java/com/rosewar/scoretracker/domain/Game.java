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
    @JoinColumn(name = "player1_id", nullable = true) // User 테이블의 userId를 참조하는 외래 키. 비회원의 회원 정보를 저장하지 않으므로 nullable
    private User player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = true) // User 테이블의 userId를 참조하는 외래 키. 비회원의 회원 정보를 저장하지 않으므로 nullable
    private User player2;

    private int score1;

    private int score2;
}
