package com.rosewar.scoretracker.service;

import com.rosewar.scoretracker.domain.Post;
import com.rosewar.scoretracker.domain.User;
import com.rosewar.scoretracker.dto.request.PostRequestDTO;
import com.rosewar.scoretracker.dto.response.PostResponseDTO;
import com.rosewar.scoretracker.repository.PostRepository;
import com.rosewar.scoretracker.repository.UserRepository;
import com.rosewar.scoretracker.util.DTOMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.rosewar.scoretracker.util.DTOMapper.toPostResponseDTO;

@Service
@Transactional
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // 게시물 생성
    @Transactional
    public PostResponseDTO createPost(PostRequestDTO postDTO) {
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return toPostResponseDTO(savedPost);
    }

    // 특정 게시물 조회
    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return toPostResponseDTO(post);
    }

    // 모든 게시물 조회
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(DTOMapper::toPostResponseDTO)
                .collect(Collectors.toList());
    }

    // 게시물 수정
    @Transactional
    public PostResponseDTO updatePost(Long postId, PostRequestDTO postDTO) {
        // 기존 게시물 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // 제목과 내용 업데이트
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        // 수정된 게시물 저장
        Post updatedPost = postRepository.save(post);
        return toPostResponseDTO(updatedPost);
    }


    // 게시물 삭제
    @Transactional
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("Post not found");
        }
        postRepository.deleteById(postId);
    }

}