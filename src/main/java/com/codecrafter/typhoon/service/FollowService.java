package com.codecrafter.typhoon.service;

import static com.codecrafter.typhoon.domain.entity.Follow.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Follow;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.response.follow.FollowerResponse;
import com.codecrafter.typhoon.domain.response.follow.FollowingResponse;
import com.codecrafter.typhoon.domain.response.follow.SimpleMemberResponse;
import com.codecrafter.typhoon.exception.AlreadyExistException;
import com.codecrafter.typhoon.exception.NoMemberException;
import com.codecrafter.typhoon.repository.FollowRepository;
import com.codecrafter.typhoon.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
	private final MemberRepository memberRepository;
	private final FollowRepository followRepository;

	@Transactional(readOnly = false)
	public void followMember(Long followerId, Member me) {
		Member following = memberRepository.findById(followerId)
			.orElseThrow(NoMemberException::new);
		if (followRepository.existsByFollowerAndFollowing(me, following)) {
			throw new AlreadyExistException("이미 팔로우하고 있음!!");
		}
		Follow follow = newFollow(me, following);
		followRepository.save(follow);
	}

	@Transactional(readOnly = false)
	public void unFollowMember(Long followingId, Member me) {

		Follow follow = followRepository.findByFollowerIdAndFollowingId(me.getId(), followingId)
			.orElseThrow(() -> new NoMemberException("팔로잉하고있지 앟음!!"));
		followRepository.delete(follow);
	}

	public FollowingResponse getFollowings(Long followerId) {
		List<Follow> follows = followRepository.findByFollowerId(followerId);

		List<SimpleMemberResponse> followings = follows.stream()
			.map(follow -> new SimpleMemberResponse(follow.getFollowing(), follow.getCreatedAt()))
			.toList();

		return new FollowingResponse(followerId, followings);
	}

	public FollowerResponse getFollowers(Long followingId) {
		List<Follow> follows = followRepository.findByFollowingId(followingId);

		List<SimpleMemberResponse> followings = follows.stream()
			.map(follow -> new SimpleMemberResponse(follow.getFollower(), follow.getCreatedAt()))
			.toList();

		return new FollowerResponse(followingId, followings);
	}
}
