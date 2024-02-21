package com.codecrafter.typhoon.domain.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostCreateRequest {

	@NotBlank(message = "제목은 필수")
	@Size(max = 50, message = "최대50자")
	private String title;

	@NotBlank(message = "컨텐츠는 필수")
	private String content;

	private List<MultipartFile> images;

	private List<String> hashtag;

}
