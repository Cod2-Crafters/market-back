package com.codecrafter.typhoon.domain.response.follow;

import java.util.List;

/**
 * 내가 팔로우하는 (팔로잉당하는) 사람들
 *
 * @param followerId
 * @param followingList
 */
public record FollowingResponse(
	Long followerId,
	List<FollowMemberResponse> followingList

) {
	public FollowingResponse(Long followerId, List<FollowMemberResponse> followingList) {
		this.followerId = followerId;
		this.followingList = followingList;
	}
}

