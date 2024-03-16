package com.codecrafter.typhoon.domain.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품상세페이지에 들어갈 상점정보
 */
@NoArgsConstructor
@Getter
public class PostShopResponse {

	private Long memberId;

	private String shopName;

	private String logoPath;

	private Long postCount;

	private Long followerCount;

	private List<SimplePostDto> simplePostDtoList = new ArrayList<>();

	public PostShopResponse(Long memberId, String shopName, String logoPath, Long postCount, Long followerCount) {
		this.memberId = memberId;
		this.shopName = shopName;
		this.logoPath = logoPath;
		this.postCount = postCount;
		this.followerCount = followerCount;
	}

	public void seTthumbnailAndPriceList(List<SimplePostDto> simplePostDtoList) {
		this.simplePostDtoList = simplePostDtoList;
	}
}
