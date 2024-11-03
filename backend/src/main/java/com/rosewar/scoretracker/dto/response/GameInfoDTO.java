package com.rosewar.scoretracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameInfoDTO {

    private String userId;
    private int maxScore;
    private int totalPlayCount;
    private int winCount;
    private int failCount;
}