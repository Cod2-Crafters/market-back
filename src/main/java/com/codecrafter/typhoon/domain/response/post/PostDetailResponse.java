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
	/**
	 * postId
	 */
	private Long id;
	/**
	 * 간단유저정보
	 */
	private SimpleMemberResponse member;
	/**
	 * 제목
	 */
	private String title;
	/**
	 * 내용
	 */
	private String content;
	/**
	 * 판매상태
	 */
	private PostStatus status;
	/**
	 * 가격
	 */
	private Integer price;
	/**
	 * 삭제여부
	 */
	private boolean isDeleted;
	/**
	 * 이미지들
	 */
	private List<SimplePostImageResponse> ImageList;
	/**
	 * 생성시간
	 */
	private LocalDateTime createdAt;
	/**
	 * 마지막 수정시간
	 */
	private LocalDateTime modifyedAt;

	/**
	 * 해시태그 목록
	 */
	@Setter
	private List<String> hashtagList;

	/**
	 * 이 상품을 북마크한 수
	 */
	@Setter
	private int bookmarkCount;

	@Setter
	/** 조회수*/
	private Long viewCount;

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

