package com.codecrafter.typhoon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.config.resolver.CurrentMember;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.response.follow.FollowerResponse;
import com.codecrafter.typhoon.domain.response.follow.FollowingResponse;
import com.codecrafter.typhoon.service.FollowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;

	// 팔로잉 생성
	@PostMapping("/{memberId}")
	public ResponseEntity<Void> followMember(@PathVariable Long memberId, @CurrentMember Member me) {
		followService.followMember(memberId, me);
		return ResponseEntity.ok().build();
	}

	// 언팔로잉
	@DeleteMapping("/{followingId}")
	public ResponseEntity<Void> unfollowMember(@PathVariable Long followingId, @CurrentMember Member me) {
		followService.unFollowMember(followingId, me);
		return ResponseEntity.ok().build();
	}

	// (내가) 팔로잉하는 사람들 목록 조회
	@GetMapping("/{memberId}/followings")
	public ResponseEntity<FollowingResponse> getFollowings(@PathVariable Long memberId) {
		FollowingResponse followings = followService.getFollowings(memberId);
		return ResponseEntity.ok(followings);
	}

	// (나를) 팔로우하는 사람들 목록 조회
	@GetMapping("/{memberId}/followers")
	public ResponseEntity<FollowerResponse> getFollowers(@PathVariable Long memberId) {
		FollowerResponse followers = followService.getFollowers(memberId);
		return ResponseEntity.ok(followers);
	}
}

