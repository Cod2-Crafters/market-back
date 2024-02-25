package com.codecrafter.typhoon.controller;

import io.swagger.v3.oas.annotations.Operation;
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

	@Operation(summary = "팔로잉",
			description = "★타상점에 팔로잉 걸기</br>"
						+ "MemberId = 숫자")
	@PostMapping("/{memberId}")
	public ResponseEntity<Void> followMember(@PathVariable Long memberId, @CurrentMember Member me) {
		followService.followMember(memberId, me);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "언팔로잉",
			description = "★팔로잉 끊기</br>"
						+ "FollowingId = 팔로잉번호(숫자)")
	@DeleteMapping("/{followingId}")
	public ResponseEntity<Void> unfollowMember(@PathVariable Long followingId, @CurrentMember Member me) {
		followService.unFollowMember(followingId, me);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "팔로잉 목록 조회",
			description = "★내가 팔로잉 요청한 상점목록 조회</br>"
						+ "MemberId = 숫자</br>"
						+ "{host}/api/follow/2/followings")
	@GetMapping("/{memberId}/followings")
	public ResponseEntity<FollowingResponse> getFollowings(@PathVariable Long memberId) {
		FollowingResponse followings = followService.getFollowings(memberId);
		return ResponseEntity.ok(followings);
	}

	@Operation(summary = "팔로워 목록 조회",
			description = "★나를 팔로잉 걸은 상점목록 조회</br>"
						+ "MemberId = 숫자</br>"
						+ "{host}/api/follow/2/followers")
	@GetMapping("/{memberId}/followers")
	public ResponseEntity<FollowerResponse> getFollowers(@PathVariable Long memberId) {
		FollowerResponse followers = followService.getFollowers(memberId);
		return ResponseEntity.ok(followers);
	}
}

