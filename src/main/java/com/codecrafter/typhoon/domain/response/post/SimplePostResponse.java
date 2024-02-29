package com.codecrafter.typhoon.domain.response.post;

import java.time.LocalDateTime;

import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.enumeration.PostStatus;
import com.codecrafter.typhoon.domain.response.SimpleMemberResponse;

public record SimplePostResponse(

	Long id,
	SimpleMemberResponse simpleMemberResponse,
	String title,

	Integer price,
	String content,
	PostStatus postStatus,
	boolean isDeleted,
	String thumbnailPath,

	LocalDateTime createdAt

) {
	public SimplePostResponse(Post post) {
		this(
			post.getId(),
			new SimpleMemberResponse(post.getMember()),
			post.getTitle(),
			post.getPrice(),
			post.getContent(),
			post.getStatus(),
			post.isDeleted(),
			post.getThumbnailPath(),
			post.getCreatedAt()
		);
	}

}
