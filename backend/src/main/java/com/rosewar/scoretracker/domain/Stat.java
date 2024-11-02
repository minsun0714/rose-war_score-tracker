package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Stat {
    @Id
    private String userId;

    @OneToOne
    @MapsId // userId가 기본 키이면서 외래 키로 User와의 관계를 설정
    @JoinColumn(name = "user_id") // User 테이블의 userId를 참조하는 외래 키
    private User user;

    private int maxScore;

    private int totalPlayCount;

    private int winCount;

    private int failCount;
}
