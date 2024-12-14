package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.request.GameResultRequestDTO;
import com.rosewar.scoretracker.dto.response.GameResultResponseDTO;
import com.rosewar.scoretracker.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameResultController {

    private final GameService gameService;

    // 게임 결과 생성
    @PostMapping
    public ResponseEntity<GameResultResponseDTO> createGameResult(@RequestBody GameResultRequestDTO gameRequestDTO) {
        System.out.println(Arrays.deepToString(gameRequestDTO.getGameBoard()));
        GameResultResponseDTO createdGameResult = gameService.createResult(gameRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGameResult);
    }
}
