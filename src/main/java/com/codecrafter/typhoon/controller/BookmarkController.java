package com.codecrafter.typhoon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {
/*
    // 북마크 등록
    @PostMapping("/{postId}")
    public ResponseEntity<?> addBookmark(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 북마크 등록 로직 구현
        // userDetails에서 북마크를 추가하는 유저의 ID를 가져올 수 있음
        bookmarkService.addBookmark(userDetails.getUserId(), postId);

        return ResponseEntity.ok().body("Bookmark added successfully for post with id " + postId);
    }

    // 북마크 취소
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> removeBookmark(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 북마크 취소 로직 구현
        // userDetails를 사용하여 요청한 유저가 해당 postId의 북마크를 취소할 권한이 있는지 확인
        bookmarkService.removeBookmark(userDetails.getUserId(), postId);

        return ResponseEntity.ok().body("Bookmark removed successfully for post with id " + postId);
    }
    */
}
