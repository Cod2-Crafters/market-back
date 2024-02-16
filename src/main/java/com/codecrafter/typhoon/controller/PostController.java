package com.codecrafter.typhoon.controller;

import com.codecrafter.typhoon.domain.request.DraftRequest;
import com.codecrafter.typhoon.domain.request.PostRequest;
import com.codecrafter.typhoon.domain.response.PostResponse;
import com.codecrafter.typhoon.repository.post.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    PostRepository postRepository;
    // 상품 등록 화면 조회
    @GetMapping("/new")
    public ResponseEntity<?> getNewPostScreen() {
        return ResponseEntity.ok().build();
    }

    // 상품 등록
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("createPost");
    }

    // 상품 수정
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest) {
        return ResponseEntity.ok().body("updatePost");
    }

    // 상품 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return ResponseEntity.ok().body("deletePost");
    }

    // 임시 저장
    @PostMapping("/draft")
    public ResponseEntity<?> saveDraft(@RequestBody DraftRequest draftRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body("draft");
    }

    // 임시 저장불러오기
    @GetMapping("/draft/{draftId}")
    public ResponseEntity<?> getDraft(@PathVariable Long draftId) {
        return ResponseEntity.ok().body("getDraft");
    }

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts(@RequestParam(required = false) Long categoryId) {
        List postList = null;
        return ResponseEntity.ok(postList);
    }

    // 상품 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long postId) {
        PostResponse postResponse = null;
        return ResponseEntity.ok(postResponse);
    }
    // 해시태그 등록
    @PostMapping("/{postId}/hashtags")
    public ResponseEntity<?> addHashtags(@PathVariable Long postId, @RequestBody List<String> hashtags) {
        return ResponseEntity.status(HttpStatus.CREATED).body("addHashtags");
    }

    // 해시태그 삭제
    @DeleteMapping("/{postId}/hashtags")
    public ResponseEntity<?> removeHashtags(@PathVariable Long postId, @RequestBody List<String> hashtags) {
        return ResponseEntity.ok().body("removeHashtags");
    }

}
