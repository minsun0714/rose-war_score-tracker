package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글과 댓글을 함께 조회
    @EntityGraph(attributePaths = {"comments"})
    Post findByPostId(String postId);

    // 최신 5개의 글 조회 (생성일 기준 내림차순 정렬)
    List<Post> findTop5ByOrderByCreatedAtDesc();

    // 인기글 5개 조회 (좋아요 개수 기준 내림차순 정렬)
    List<Post> findTop5ByOrderByLikeCountAtDesc();
}
