package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.response.StatResponseDTO;
import com.rosewar.scoretracker.service.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
public class StatController {

    private final StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    // 특정 사용자의 통계 조회
    @GetMapping("/{userId}")
    public ResponseEntity<StatResponseDTO> getStatByUserId(@PathVariable String userId) {
        StatResponseDTO stat = statService.getStatByUserId(userId);
        return ResponseEntity.ok(stat);
    }
}
