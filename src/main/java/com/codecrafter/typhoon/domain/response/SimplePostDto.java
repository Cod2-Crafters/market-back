package com.codecrafter.typhoon.domain.response;

import lombok.Getter;

@Getter
public class SimplePostDto {

	private Long id;

	private String thumbnailPath;

	private int price;

	public SimplePostDto(Long id, String thumbnailPath, int price) {
		this.id = id;
		this.thumbnailPath = thumbnailPath;
		this.price = price;
	}
}
