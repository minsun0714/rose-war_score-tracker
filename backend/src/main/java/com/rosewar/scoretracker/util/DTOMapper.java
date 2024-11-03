package com.rosewar.scoretracker.util;

import com.rosewar.scoretracker.domain.Stat;
import com.rosewar.scoretracker.domain.User;
import com.rosewar.scoretracker.domain.Post;
import com.rosewar.scoretracker.domain.Comment;
import com.rosewar.scoretracker.dto.response.StatResponseDTO;
import com.rosewar.scoretracker.dto.response.UserInfoDTO;
import com.rosewar.scoretracker.dto.response.PostResponseDTO;
import com.rosewar.scoretracker.dto.response.CommentResponseDTO;

public class DTOMapper {

    private DTOMapper() {} // 인스턴스화 방지

    // User -> UserInfoDTO 변환
    public static UserInfoDTO toUserInfoDTO(User user) {
        return UserInfoDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .build();
    }

    // Post -> PostResponseDTO 변환
    public static PostResponseDTO toPostResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getPostId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(toUserInfoDTO(post.getUser())) // 중첩된 변환
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
                .writer(toUserInfoDTO(comment.getUser())) // 중첩된 변환
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
                .userId(stat.getUser().getUserId())
                .maxScore(stat.getMaxScore())
                .totalPlayCount(stat.getTotalPlayCount())
                .winCount(stat.getWinCount())
                .failCount(stat.getFailCount())
                .build();
    }

}
