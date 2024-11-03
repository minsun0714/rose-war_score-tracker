package com.rosewar.scoretracker.dto.response;

import com.rosewar.scoretracker.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {

    private Long commentId;
    private Long postId;          // 댓글이 속한 게시물 ID 추가
    private UserInfoDTO writer;          // 댓글 작성자 ID
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likeCount;

    private Long parentCommentId;       // 부모 댓글 정보 (답글일 경우)
    private List<CommentResponseDTO> childrenComments; // 답글 리스트
}
