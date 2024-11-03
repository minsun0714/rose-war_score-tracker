package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
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

    @OneToOne(mappedBy = "player", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Stat stat; // Stat과의 1:1 관계
}
