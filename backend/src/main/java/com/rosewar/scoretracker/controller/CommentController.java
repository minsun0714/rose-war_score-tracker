package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.request.CommentRequestDTO;
import com.rosewar.scoretracker.dto.response.CommentResponseDTO;
import com.rosewar.scoretracker.dto.response.CommentResponseWrapperDTO;
import com.rosewar.scoretracker.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @PathVariable Long postId, @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO createdComment = commentService.createComment(postId, commentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    // 특정 게시물의 모든 댓글 조회
    @GetMapping
    public ResponseEntity<CommentResponseWrapperDTO> getCommentsByPostId(@PathVariable Long postId) {
        CommentResponseWrapperDTO comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // 특정 게시물의 모든 댓글 개수 조회
    @GetMapping("/count")
    public ResponseEntity<Integer> getCommentsCountByPostId(@PathVariable Long postId) {
         int commentTotalLength = commentService.getCommentsCountByPostId(postId);
        return ResponseEntity.ok(commentTotalLength);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @Valid
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO updatedComment = commentService.updateComment(commentId, commentRequestDTO);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
