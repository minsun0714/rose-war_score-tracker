package com.rosewar.scoretracker.util;

import com.rosewar.scoretracker.domain.*;
import com.rosewar.scoretracker.dto.response.*;

public class DTOMapper {

    private DTOMapper() {} // 인스턴스화 방지

    // User -> UserInfoDTO 변환
    public static UserInfoDTO toUserInfoDTO(Player player) {
        return UserInfoDTO.builder()
                .userId(player.getUserId())
                .name(player.getName())
                .nickname(player.getNickname())
                .profileImg(player.getProfileImg())
                .build();
    }

    // Player와 accessToken을 이용해 SignUpResponseDTO로 변환
    public static SignUpResponseDTO toSignUpResponseDTO(Player player, String accessToken) {
        return SignUpResponseDTO.builder()
                .name(player.getName())
                .userId(player.getUserId())
                .nickname(player.getNickname())
                .profileImg(player.getProfileImg())
                .accessToken(accessToken)
                .build();
    }

    // Post -> PostResponseDTO 변환
    public static PostResponseDTO toPostResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(toUserInfoDTO(post.getPlayer())) // 중첩된 변환
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .likeCount(post.getLikeCount())
                .build();
    }

    // Comment -> CommentResponseDTO 변환
    public static CommentResponseDTO toCommentResponseDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .writer(toUserInfoDTO(comment.getPlayer())) // 중첩된 변환
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .likeCount(comment.getLikeCount())
                .parentCommentId(comment.getParent() != null ? comment.getParent().getCommentId() : null)
                .build();
    }

    // Stat을 StatResponseDTO로 변환하는 헬퍼 메서드
    public static StatResponseDTO toStatResponseDTO(Stat stat) {
        return StatResponseDTO.builder()
                .userId(stat.getPlayer().getUserId())
                .maxScore(stat.getMaxScore())
                .totalPlayCount(stat.getTotalPlayCount())
                .winCount(stat.getWinCount())
                .failCount(stat.getFailCount())
                .build();
    }

    public static PostLikeResponseDTO toPostLikeResponseDTO(PostLike postLike){
        return PostLikeResponseDTO.builder()
                .postLikeId(postLike.getLikeId())
                .post(postLike.getPost())
                .player(postLike.getPlayer())
                .build();
    }
}
