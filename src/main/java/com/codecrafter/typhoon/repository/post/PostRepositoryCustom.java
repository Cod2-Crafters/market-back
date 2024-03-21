package com.codecrafter.typhoon.repository.post;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.codecrafter.typhoon.domain.SearchCondition;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.response.SimplePostDto;

public interface PostRepositoryCustom {

	// Slice<Post> getPostList(Pageable pageable);

	Long getTotalPostViewCount(long postId);

	List<SimplePostDto> getSimplePostDtoList(List<Long> postIdList);

	Slice<Post> search(SearchCondition searchCondition, Pageable pageable);
}
