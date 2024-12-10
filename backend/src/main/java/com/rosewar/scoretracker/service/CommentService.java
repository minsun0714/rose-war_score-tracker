package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Comment;
import com.rosewar.scoretracker.domain.Post;
import com.rosewar.scoretracker.domain.Player;
import com.rosewar.scoretracker.dto.request.CommentRequestDTO;
import com.rosewar.scoretracker.dto.response.CommentResponseDTO;
import com.rosewar.scoretracker.dto.response.CommentResponseWrapperDTO;
import com.rosewar.scoretracker.repository.CommentRepository;
import com.rosewar.scoretracker.repository.PostRepository;
import com.rosewar.scoretracker.repository.UserRepository;
import com.rosewar.scoretracker.util.DTOMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.rosewar.scoretracker.security.SecurityUtil.getAuthenticatedMemberId;
import static com.rosewar.scoretracker.util.DTOMapper.toCommentResponseDTO;
import static com.rosewar.scoretracker.util.DTOMapper.toCommentResponseWrapperDTO;

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
    public CommentResponseDTO createComment(Long postId, CommentRequestDTO commentDTO) {
        String userId = getAuthenticatedMemberId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Player player = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = Comment.builder()
                .post(post)
                .player(player)
                .content(commentDTO.getContent())
                .build();

        if (commentDTO.getParentId() != null) {
            Comment parentComment = commentRepository.findById(commentDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));
            comment.setParent(parentComment); // 부모 설정
            parentComment.getChildren().add(comment); // 부모의 children에 추가
        }

        Comment savedComment = commentRepository.save(comment);
        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);
        return toCommentResponseDTO(savedComment);
    }

    public CommentResponseWrapperDTO getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 모든 댓글을 조회
        List<Comment> allComments = commentRepository.findByPost(post);

        // Comment 객체를 CommentResponseDTO로 변환
        List<CommentResponseDTO> responseDTOs = allComments.stream()
                .map(DTOMapper::toCommentResponseDTO)
                .toList();
        System.out.println(responseDTOs);

        return toCommentResponseWrapperDTO(responseDTOs, allComments.size());

//        // 부모-자식 관계 매칭
//        for (CommentResponseDTO parent : responseDTOs) {
//            for (CommentResponseDTO child : responseDTOs) {
//                if (child.getParentCommentId() != null && child.getParentCommentId().equals(parent.getCommentId())) {
//                    if (parent.getChildrenComments() == null) {
//                        parent.setChildrenComments(new ArrayList<>());
//                    }
//                    parent.getChildrenComments().add(child);
//                }
//            }
//        }
//
//        // 최상위 댓글만 반환
//        return responseDTOs.stream()
//                .filter(comment -> comment.getParentCommentId() == null)
//                .toList();
    }

    public int getCommentsCountByPostId(Long postId) {
        return commentRepository.getCommentCountByPostId(postId);
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
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        Post post = postRepository.findById(comment.getPost().getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        commentRepository.deleteById(comment.getCommentId());
        post.setCommentCount(post.getCommentCount() - 1);
        postRepository.save(post);
    }
}
