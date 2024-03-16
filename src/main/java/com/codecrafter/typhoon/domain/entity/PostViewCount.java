package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private Long postId;

	private Long viewCount = 0L;

	private LocalDate viewDay;

	/**
	 * 유일하게 생성 가능
	 *
	 * @param postId    postId
	 * @param viewCount 조회수
	 * @param viewDay   날짜 (배치로돌면 어제일거임)
	 * @return
	 */
	public static PostViewCount of(Long postId, Long viewCount, LocalDate viewDay) {
		PostViewCount postViewCount = new PostViewCount();
		postViewCount.postId = postId;
		postViewCount.viewCount = viewCount;
		postViewCount.viewDay = viewDay;
		return postViewCount;
	}

}
