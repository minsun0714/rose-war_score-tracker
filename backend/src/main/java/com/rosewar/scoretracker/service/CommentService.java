package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Comment;
import com.rosewar.scoretracker.domain.Post;
import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.dto.request.CommentRequestDTO;
import com.rosewar.scoretracker.dto.response.CommentResponseDTO;
import com.rosewar.scoretracker.repository.CommentRepository;
import com.rosewar.scoretracker.repository.PostRepository;
import com.rosewar.scoretracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.rosewar.scoretracker.util.DTOMapper.toUserInfoDTO;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CommentResponseDTO createComment(CommentRequestDTO commentDTO) {
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Player player = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setPlayer(player);
        comment.setContent(commentDTO.getContent());

        if (commentDTO.getParentId() != null) {

            Comment parentComment = commentRepository.findById(commentDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
            comment.setParent(parentComment); // 부모 설정
            parentComment.getChildren().add(comment); // 부모의 children에 추가
        }

        Comment savedComment = commentRepository.save(comment);
        return toCommentResponseDTO(savedComment);
    }

    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 모든 댓글을 조회
        List<Comment> allComments = commentRepository.findByPost(post);

        // Comment 객체를 CommentResponseDTO로 변환
        List<CommentResponseDTO> responseDTOs = allComments.stream()
                .map(this::toCommentResponseDTO)
                .toList();

        // 부모-자식 관계 매칭
        for (CommentResponseDTO parent : responseDTOs) {
            for (CommentResponseDTO child : responseDTOs) {
                if (child.getParentCommentId() != null && child.getParentCommentId().equals(parent.getCommentId())) {
                    if (parent.getChildrenComments() == null) {
                        parent.setChildrenComments(new ArrayList<>());
                    }
                    parent.getChildrenComments().add(child);
                }
            }
        }

        // 최상위 댓글만 반환
        return responseDTOs.stream()
                .filter(comment -> comment.getParentCommentId() == null)
                .toList();
    }


    // 댓글 수정
    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return toCommentResponseDTO(updatedComment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Comment not found");
        }
        commentRepository.deleteById(commentId);
    }

    // Comment를 CommentResponseDTO로 변환하는 헬퍼 메서드
    private CommentResponseDTO toCommentResponseDTO(Comment comment) {
        return CommentResponseDTO.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .writer(toUserInfoDTO(comment.getPlayer()))
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .likeCount(comment.getLikeCount())
                .parentCommentId(comment.getParent() != null ? comment.getParent().getCommentId() : null)
                .build();
    }
}
