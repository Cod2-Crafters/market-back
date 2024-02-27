package com.codecrafter.typhoon.domain.request;

import java.util.List;

import com.codecrafter.typhoon.domain.entity.PostImage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * SimplePostResponse 생성시 받는 request 객체
 *
 * @param title                제목
 * @param content              내용
 * @param postImageRequestList 이미지패스와, 섬네일여부
 * @param hashTagList          해시태그 모음
 */
public record PostCreateRequest(

	Long categoryId,

	@NotBlank(message = "제목은 필수") @Size(max = 50, message = "최대50자") String title,
	@NotBlank(message = "컨텐츠는 필수") String content,
	List<ImageRequest> postImageRequestList,
	List<String> hashTagList,

	Integer price
) {

	public static record ImageRequest(String imagePath, Boolean isThumbnail) {
		public PostImage toEntity() {
			return PostImage.createPostImage(imagePath(), isThumbnail());
		}
	}
}
