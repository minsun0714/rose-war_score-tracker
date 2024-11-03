package com.rosewar.scoretracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class MyInfoUpdateDTO {

    @Nullable
    private String profileImg; // 프로필 이미지 URL 또는 파일 경로 (선택 사항이므로 유효성 검사 없음)

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;
}
