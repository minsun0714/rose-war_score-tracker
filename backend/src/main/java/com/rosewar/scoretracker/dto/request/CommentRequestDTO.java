package com.rosewar.scoretracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequestDTO {

    @NotBlank(message = "Content is required")
    private String content; // 댓글 내용

    @NotBlank(message = "User ID is required")
    private String userId; // 작성자 ID

    @NotBlank(message = "Post ID is required")
    private Long postId; // 댓글이 속한 게시물 ID

    private Long parentCommentId; // 부모 댓글 ID (대댓글일 경우), 없을 경우 null
}
