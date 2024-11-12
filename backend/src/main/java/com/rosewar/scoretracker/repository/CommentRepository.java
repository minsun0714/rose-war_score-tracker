package com.rosewar.scoretracker.repository;

import com.rosewar.scoretracker.domain.Comment;
import com.rosewar.scoretracker.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post); // 수정된 메서드
}
