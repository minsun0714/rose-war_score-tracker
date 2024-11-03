package com.rosewar.scoretracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private String userId;
    private String name;
    private String nickname;
    private String profileImg;
}