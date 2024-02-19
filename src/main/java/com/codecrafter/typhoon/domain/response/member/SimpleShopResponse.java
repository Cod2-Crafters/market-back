package com.codecrafter.typhoon.domain.response.member;

import java.time.LocalDateTime;

import com.codecrafter.typhoon.domain.entity.Member;

public record SimpleShopResponse(
	Long id,
	String email,
	String shopName,
	String logoPath,
	LocalDateTime createdAt
) {
	public SimpleShopResponse(Member member) {
		this(member.getId(), member.getEmail(), member.getShopName(), member.getLogoPath(), member.getCreatedAt());
	}
}
