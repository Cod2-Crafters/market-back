package com.codecrafter.typhoon.domain.response.post;

import java.time.LocalDateTime;
import java.util.List;

import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.enumeration.PostStatus;
import com.codecrafter.typhoon.domain.response.SimpleMemberResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 포스트와 연관된 정보들 한방에
 */
@Getter
@ToString
public class PostDetailResponse {
	private Long id;
	private SimpleMemberResponse member;
	private String title;
	private String content;
	private PostStatus status;
	private Integer price;
	private boolean isDeleted;
	private List<SimplePostImageResponse> ImageList;
	private LocalDateTime createdAt;
	private LocalDateTime modifyedAt;

	@Setter
	private List<String> hashtagList;

	public PostDetailResponse(Post post) {
		this.id = post.getId();
		this.member = new SimpleMemberResponse(post.getMember());
		this.price = post.getPrice();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.status = post.getStatus();
		this.isDeleted = post.isDeleted();
		this.createdAt = post.getCreatedAt();
		this.modifyedAt = post.getModifiedAt();
		this.ImageList = post.getPostImageList()
			.stream()
			.map(SimplePostImageResponse::new)
			.toList();
	}

}

