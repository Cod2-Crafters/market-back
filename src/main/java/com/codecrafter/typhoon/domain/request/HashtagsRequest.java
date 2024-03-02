package com.codecrafter.typhoon.domain.request;

import java.util.List;

public record HashtagsRequest(
	List<String> hashtagList
) {
}
