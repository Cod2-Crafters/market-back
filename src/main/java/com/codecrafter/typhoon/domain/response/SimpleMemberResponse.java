package com.codecrafter.typhoon.domain.response;

import java.time.LocalDateTime;

import com.codecrafter.typhoon.domain.entity.Member;

public record SimpleMemberResponse(
	Long id,
	String email,
	String shopName,
	String logoPath,
	LocalDateTime createdAt
) {
	public SimpleMemberResponse(Member member) {
		this(
			member.getId(),
			member.getEmail(),
			member.getShopName(),
			member.getLogoPath(),
			member.getCreatedAt()
		);
	}
}
