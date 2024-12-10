package com.rosewar.scoretracker.dto.request;

import lombok.Data;

@Data
public class GameResultRequestDTO {

    private int[][] gameBoard = new int[10][10]; // 10x10 게임 보드 (기본값 설정)
}
