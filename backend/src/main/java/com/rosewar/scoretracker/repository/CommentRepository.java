package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.Comment;
import com.rosewar.scoretracker.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post); // 수정된 메서드

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.postId = :postId")
    int getCommentCountByPostId(@Param("postId") Long postId);
}
