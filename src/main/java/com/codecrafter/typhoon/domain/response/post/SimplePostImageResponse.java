package com.codecrafter.typhoon.domain.response.post;

import com.codecrafter.typhoon.domain.entity.PostImage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties()
public record SimplePostImageResponse(

	Long id,
	String imagePath,
	boolean isThumbnail
) {
	public SimplePostImageResponse(PostImage postImage) {
		this(
			postImage.getId(),
			postImage.getImagePath(),
			postImage.isThumbnail()
		);
	}
}
