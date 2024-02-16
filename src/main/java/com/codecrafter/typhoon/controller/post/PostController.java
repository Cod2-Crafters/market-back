package com.codecrafter.typhoon.controller.post;

import com.codecrafter.typhoon.domain.request.PostRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    // 상품 등록 화면 조회
    @GetMapping("/new")
    public ResponseEntity<?> getNewPostScreen() {
        // 상품 등록 화면 관련 로직
        // 예: 필요한 정보를 로드하여 클라이언트에 반환
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
        // 상품 수정 로직 구현
        // 예: postService.updatePost(postId, postRequest);
        return ResponseEntity.ok().body("Post updated successfully");
    }

    // 상품 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        // 상품 삭제 로직 구현
        // 예: postService.deletePost(postId);
        return ResponseEntity.ok().body("Post deleted successfully");
    }

    // 임시 저장
    @PostMapping("/draft")
    public ResponseEntity<?> saveDraft(@RequestBody DraftRequest draftRequest) {
        // 임시 저장 로직 구현
        // 예: postService.saveDraft(draftRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Draft saved successfully");
    }

    // 임시 저장불러오기
    @GetMapping("/draft/{draftId}")
    public ResponseEntity<?> getDraft(@PathVariable Long draftId) {
        // 임시 저장된 내용 불러오기 로직 구현
        // 예: postService.getDraft(draftId);
        return ResponseEntity.ok().body("Draft loaded successfully");
    }

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts(@RequestParam(required = false) Long categoryId) {
        // 상품 목록 조회 로직 구현
        // 예: List<PostResponse> posts = postService.getPosts(categoryId);
        return ResponseEntity.ok(posts);
    }

    // 상품 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long postId) {
        // 상품 상세 조회 로직 구현
        // 예: PostResponse post = postService.getPostDetail(postId);
        return ResponseEntity.ok(post);
    }
}
