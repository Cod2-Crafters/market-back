package com.codecrafter.typhoon.domain.entity;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 포스트 조회수 저장용
 */
@Entity
@ToString
@NoArgsConstructor(access = PROTECTED)
public class PostViewCount {

	@Id
	private Long postId;

	private Long viewCount = 0L;

	@UpdateTimestamp
	private LocalDateTime lastViewedAt;

	public void incrementViewCount(long viewCount) {
		this.viewCount += viewCount;
	}

	private PostViewCount(Long postId) {
		this.postId = postId;
	}

	public static PostViewCount from(Long postId) {
		return new PostViewCount(postId);
	}

}
