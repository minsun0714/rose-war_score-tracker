package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;

    @GetMapping("/{postId}")
    public ResponseEntity<Boolean> getLikeStatus(@PathVariable Long postId){
        Boolean status = postLikeService.getLikeStatus(postId);
        return ResponseEntity.ok(status);
    }
    // 좋아요 추가
    @PatchMapping("/{postId}")
    public ResponseEntity<Boolean> toggleLike(@PathVariable Long postId) {
        Boolean result = postLikeService.toggleLike(postId);
        return ResponseEntity.ok(result);
    }
}
