package com.codecrafter.typhoon.domain;

import java.time.LocalDateTime;

import com.codecrafter.typhoon.domain.enumeration.PostStatus;

import jakarta.validation.constraints.Min;

public record SearchCondition(

	/* 계시글번호*/
	Long postId,

	/* 계시글제목*/
	String postTitle,

	PostStatus postStatus,

	/* 최소가격 */
	@Min(value = 0, message = "최소 가격은 0보다 크거나 같아야 합니다.")
	Long minPrice,

	/* 최대가격 */
	Long maxPrice,

	LocalDateTime minCreatedAt,

	LocalDateTime maxCreatedAt,

	/* 상점Id */
	Long shopId,
	/* 상점명 */
	String shopName,

	/* 카테고리Id*/
	Long categoryId,

	/* 해시태그이름*/
	String tagName

) {
}
