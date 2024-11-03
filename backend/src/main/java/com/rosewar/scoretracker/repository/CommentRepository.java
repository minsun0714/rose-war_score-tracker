package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId); // 특정 게시물의 모든 댓글 조회
    // 특정 부모 댓글에 연결된 모든 대댓글 조회
    List<Comment> findByParent(Comment parent);
}
