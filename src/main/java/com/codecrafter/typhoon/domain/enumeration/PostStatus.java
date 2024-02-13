package com.codecrafter.typhoon.domain.enumeration;

import lombok.Getter;

@Getter
public enum PostStatus {
	ON_SALE("판매중"),
	RESERVED("예약중"),
	SOLD_OUT("판매완료");

	private final String describe;

	PostStatus(String describe) {
		this.describe = describe;
	}

}
