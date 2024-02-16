package com.codecrafter.typhoon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
    // 팔로잉 생성
    @PostMapping("/{memberId}")
    public ResponseEntity<?> followMember(@PathVariable Long memberId) {
        return ResponseEntity.ok().body("followMember");
    }

    // 언팔로잉
    @DeleteMapping("/{followId}")
    public ResponseEntity<?> unfollowMember(@PathVariable Long followI) {
        return ResponseEntity.ok().body("unfollowMember");
    }

    // 팔로잉 목록 조회
    @GetMapping("/{memberId}/followings")
    public ResponseEntity<List<?>> getFollowings(@PathVariable Long memberId) {
        List followList = null;
        return ResponseEntity.ok(followList);
    }

    // 팔로워 목록 조회
    @GetMapping("/{memberId}/followers")
    public ResponseEntity<List<?>> getFollowers(@PathVariable Long memberId) {
        List followList = null;
        return ResponseEntity.ok(followList);
    }
}

