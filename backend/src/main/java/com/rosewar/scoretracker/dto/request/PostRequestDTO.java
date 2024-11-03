package com.rosewar.scoretracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title; // 게시물 제목

    @NotBlank(message = "Content is required")
    private String content; // 게시물 내용

    @NotBlank(message = "User ID is required")
    private String userId; // 작성자 ID
}
