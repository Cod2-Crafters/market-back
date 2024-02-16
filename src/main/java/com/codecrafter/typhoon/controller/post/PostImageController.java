package com.codecrafter.typhoon.controller.post;

import com.codecrafter.typhoon.domain.response.PostImageResponse;
import com.codecrafter.typhoon.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/images")
public class PostImageController {

    PostRepository postRepository;

    // 이미지 등록
    @PostMapping
    public ResponseEntity<?> uploadImage(@PathVariable Long postId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body("uploadImage");
    }

    // 이미지 수정
    @PutMapping("/{imageId}")
    public ResponseEntity<?> updateImage(@PathVariable Long postId, @PathVariable Long imageId, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body("updateImage");
    }
/*
    // 이미지 삭제
    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long postId, @PathVariable Long imageId) {
        return ResponseEntity.ok().body("deleteImage");
    }

    // 이미지 목록 조회
    @GetMapping
    public ResponseEntity<List<PostImageResponse>> getImages(@PathVariable Long postId) {
        return ResponseEntity.ok(images);
    }

    // 대표 썸네일 조회
    @GetMapping("/{imageId}")
    public ResponseEntity<PostImageResponse> getImage(@PathVariable Long postId, @PathVariable Long imageId) {
        return ResponseEntity.ok(postRepository);
    }
*/
}

