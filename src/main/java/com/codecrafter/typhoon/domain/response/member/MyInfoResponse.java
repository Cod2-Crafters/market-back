package com.codecrafter.typhoon.domain.response.member;

import java.time.LocalDateTime;

import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.enumeration.LoginType;

public record MyInfoResponse(
	Long id,
	String email,
	LoginType loginType,
	String shopName,
	String description,
	String logoPath,
	String realName,
	String phone,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public MyInfoResponse(Member member) {
		this(
			member.getId(),
			member.getEmail(),
			member.getLoginType(),
			member.getShopName(),
			member.getDescription(),
			member.getLogoPath(),
			member.getRealName(),
			member.getPhone(),
			member.getCreatedAt(),
			member.getModifiedAt()
		);
	}

}
