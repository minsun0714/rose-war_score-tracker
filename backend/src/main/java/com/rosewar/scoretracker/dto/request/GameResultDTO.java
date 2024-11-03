package com.rosewar.scoretracker.dto.request;

import lombok.Data;

@Data
public class GameResultDTO {

    private String player1Id; // player1의 사용자 정보
    private String player2Id; // player2의 사용자 정보

    private int[][] gameBoard = new int[10][10]; // 10x10 게임 보드 (기본값 설정)
}
