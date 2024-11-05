package com.rosewar.scoretracker.controller;

import com.rosewar.scoretracker.dto.request.PostRequestDTO;
import com.rosewar.scoretracker.dto.response.PostResponseDTO;
import com.rosewar.scoretracker.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 생성
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO createdPost = postService.createPost(postRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // 특정 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long postId) {
        PostResponseDTO post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @Valid
            @PathVariable Long postId,
            @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO updatedPost = postService.updatePost(postId, postRequestDTO);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
