package com.codecrafter.typhoon.domain.request.post;

import com.codecrafter.typhoon.domain.enumeration.PostStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(

	@NotBlank
	Long CategoryId,

	@NotBlank(message = "제목은 필수") @Size(max = 50, message = "최대50자")
	String title,
	@NotBlank(message = "컨텐츠는 필수") String content,
	@NotBlank(message = "상태는 필수") PostStatus postStatus,

	@NotBlank Integer price
) {
}
