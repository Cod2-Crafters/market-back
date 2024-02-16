package com.codecrafter.typhoon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {
    // 북마크 등록
    @PostMapping("/{postId}")
    public ResponseEntity<?> addBookmark(@PathVariable Long postId) {
        return ResponseEntity.ok().body("addBookmark");
    }

    // 북마크 취소
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> removeBookmark(@PathVariable Long postId) {
        return ResponseEntity.ok().body("removeBookmark");
    }

}
