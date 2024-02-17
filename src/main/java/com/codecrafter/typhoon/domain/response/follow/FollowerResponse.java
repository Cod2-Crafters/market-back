package com.codecrafter.typhoon.domain.response.follow;

import java.util.List;

/**
 * (나를) 팔로우하는사람들
 *
 * @param follwingId   나
 * @param followerList 나를 팔로우하는 리스트(팔로어들)
 */
public record FollowerResponse(
	Long follwingId,
	List<SimpleMemberResponse> followerList

) {
	public FollowerResponse(Long follwingId, List<SimpleMemberResponse> followerList) {
		this.follwingId = follwingId;
		this.followerList = followerList;
	}
}

