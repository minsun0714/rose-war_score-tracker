package com.rosewar.scoretracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatResponseDTO {

    private String userId;
    private int maxScore;
    private int totalPlayCount;
    private int winCount;
    private int failCount;
}