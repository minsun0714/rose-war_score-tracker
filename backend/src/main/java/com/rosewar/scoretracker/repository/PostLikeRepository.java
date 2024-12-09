package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Boolean existsByPost_PostIdAndPlayer_UserId(Long postId, String userId);

    Optional<PostLike> findByPost_PostIdAndPlayer_UserId(Long postId, String userId);
}
