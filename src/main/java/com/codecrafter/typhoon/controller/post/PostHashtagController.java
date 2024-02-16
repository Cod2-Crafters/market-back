package com.codecrafter.typhoon.controller.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostHashtagController {

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

