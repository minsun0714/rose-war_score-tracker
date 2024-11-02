package com.rosewar.scoretracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDTO {

    private String postId;
    private String title;
    private UserInfoDTO writer; // 게시글 작성자 ID
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likeCount;

    private List<CommentDTO> comments; // 댓글 리스트
}
