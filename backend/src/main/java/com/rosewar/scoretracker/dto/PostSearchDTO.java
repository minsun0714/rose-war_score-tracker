package com.rosewar.scoretracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchDTO {

    private String postId;          // 게시물 ID
    private String title;           // 게시물 제목
    private UserInfoDTO writer;     // 게시물 작성자
    private LocalDateTime createdAt; // 작성 날짜
    private int likeCount;          // 좋아요 수
}
