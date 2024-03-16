package com.codecrafter.typhoon.repository.post;

import java.util.List;

import com.codecrafter.typhoon.domain.response.SimplePostDto;

public interface PostRepositoryCustom {

	// Slice<Post> getPostList(Pageable pageable);

	Long getTotalPostViewCount(long postId);

	List<SimplePostDto> getSimplePostDtoList(List<Long> postIdList);
}
