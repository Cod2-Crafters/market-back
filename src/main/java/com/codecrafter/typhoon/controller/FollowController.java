package com.codecrafter.typhoon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
/*
    // 팔로잉 생성
    @PostMapping("/{memberId}")
    public ResponseEntity<?> followMember(@PathVariable Long memberId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body("followMember");
    }

    // 언팔로잉
    @DeleteMapping("/{followId}")
    public ResponseEntity<?> unfollowMember(@PathVariable Long followId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body("Successfully unfollowed");
    }

    // 팔로잉 목록 조회
    @GetMapping("/{memberId}/followings")
    public ResponseEntity<List<FollowDetails>> getFollowings(@PathVariable Long memberId) {
        return ResponseEntity.ok(followings);
    }

    // 팔로워 목록 조회
    @GetMapping("/{memberId}/followers")
    public ResponseEntity<List<FollowDetails>> getFollowers(@PathVariable Long memberId) {
        return ResponseEntity.ok(followers);
    }
*/
}

