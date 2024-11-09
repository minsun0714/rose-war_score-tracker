package com.rosewar.scoretracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponseDTO{
    private String userId;
    private String name;
    private String nickname;
    private String profileImg;
    private String accessToken;
}
