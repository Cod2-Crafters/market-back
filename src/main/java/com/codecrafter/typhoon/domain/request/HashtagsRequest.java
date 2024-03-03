package com.codecrafter.typhoon.domain.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record HashtagsRequest(
	@NotEmpty
	List<String> hashTagList
) {
}
