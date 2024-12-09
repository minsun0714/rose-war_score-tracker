package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.domain.Post;
import com.rosewar.scoretracker.domain.PostLike;
import com.rosewar.scoretracker.dto.response.PostLikeResponseDTO;
import com.rosewar.scoretracker.repository.PostLikeRepository;
import com.rosewar.scoretracker.repository.PostRepository;
import com.rosewar.scoretracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.rosewar.scoretracker.security.SecurityUtil.getAuthenticatedMemberId;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    public boolean getLikeStatus(Long postId){
        String userId = getAuthenticatedMemberId();
        // 좋아요 여부 확인
        return postLikeRepository.existsByPost_PostIdAndPlayer_UserId(postId, userId);
    }

    @Transactional
    public boolean toggleLike(Long postId) {
        String userId = getAuthenticatedMemberId();
        // 좋아요 여부 확인
        Optional<PostLike> existingLike = postLikeRepository.findByPost_PostIdAndPlayer_UserId(postId, userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (existingLike.isPresent()) {
            // 이미 좋아요 상태라면 삭제
            postLikeRepository.delete(existingLike.get());
            post.setLikeCount(post.getLikeCount() - 1);

            return false;
        }
        // 좋아요 상태가 아니라면 생성
        Player player = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        PostLike newLike = PostLike.builder()
                .post(post)
                .player(player)
                .build();
        postLikeRepository.save(newLike);
        post.setLikeCount(post.getLikeCount() + 1);

        return true;
    }
}
