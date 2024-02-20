package com.codecrafter.typhoon.domain.response.Boomark;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookmarkResponse {

	private Long bookmarkId;

	private Long postId;

	private String postTitle;

	private String postThumbnailPath;

	public BookmarkResponse(Long bookmarkId, Long postId, String postTitle, String postThumbnailPath) {
		this.bookmarkId = bookmarkId;
		this.postId = postId;
		this.postTitle = postTitle;
		this.postThumbnailPath = postThumbnailPath;
	}
}
