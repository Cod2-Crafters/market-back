package com.codecrafter.typhoon.domain.response.follow;

import java.time.LocalDateTime;

import com.codecrafter.typhoon.domain.entity.Member;

public record FollowMemberResponse(
	Long id,
	String email,
	String shopName,
	String logoPath,
	LocalDateTime followedAt
) {
	public FollowMemberResponse(Member member, LocalDateTime startTime) {
		this(member.getId(), member.getEmail(), member.getShopName(), member.getLogoPath(), startTime);
	}
}
