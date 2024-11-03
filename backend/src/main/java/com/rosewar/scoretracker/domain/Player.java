package com.rosewar.scoretracker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Player {

    @Id
    private String userId;
    private String password;
    private String name;
    private String nickname;
    private String profileImg;
}
