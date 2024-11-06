package com.rosewar.scoretracker.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    private String userId;
    private String password;
    private String name;
    private String nickname;
    private String profileImg;

    @OneToMany(mappedBy = "player1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> player1Game;

    @OneToMany(mappedBy = "player2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> player2Game;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stat stat; // Stat과의 1:1 관계

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts; // Player가 작성한 Post 목록

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments; // Player가 작성한 Comment 목록
}
