package com.rosewar.scoretracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResultResponseDTO {

    private Long gameId;
    private UserInfoDTO player1; // Player 1의 사용자 ID
    private UserInfoDTO player2; // Player 2의 사용자 ID
    private int score1;
    private int score2;
}
