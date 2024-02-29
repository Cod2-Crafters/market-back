package com.codecrafter.typhoon.domain.response;

import java.time.LocalDateTime;

import com.codecrafter.typhoon.domain.entity.Member;

/**
 * 상점정보
 */
public record ShopResponse(
	Long memberId,
	String email,
	String shopName,
	String description,
	String logoPath,
	LocalDateTime createdAt
) {
	public ShopResponse(Member member) {
		this(member.getId(),
			member.getEmail(),
			member.getShopName(),
			member.getDescription(), member.getLogoPath(),
			member.getCreatedAt());
	}
}
