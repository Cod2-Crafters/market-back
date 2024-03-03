package com.codecrafter.typhoon.domain.request.post;

import com.codecrafter.typhoon.domain.entity.PostImage;

public record ImageRequest(String imagePath, Boolean isThumbnail) {
	public PostImage toEntity() {
		return PostImage.createPostImage(imagePath(), isThumbnail());
	}
}
