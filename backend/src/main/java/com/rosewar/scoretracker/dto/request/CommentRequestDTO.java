package com.rosewar.scoretracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequestDTO {

    @NotBlank(message = "Content is required")
    private String content; // 댓글 내용

    @NotBlank(message = "Post ID is required")
    private Long postId; // 댓글이 속한 게시물 ID

    private Long parentId; // 부모 댓글 ID (대댓글일 경우), 없을 경우 null
}
