package com.rosewar.scoretracker.dto.response;

import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLikeResponseDTO {
    private Long postLikeId;
    private Post post;
    private Player player;
}