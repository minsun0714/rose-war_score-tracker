package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ranking {

    @Id
    private String userId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false) // Stat의 userId를 외래 키로 참조
    private Stat stat;

    private int ranking;          // 현재 순위
    private LocalDateTime updatedAt;  // 마지막 업데이트 시간
}