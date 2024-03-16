package com.codecrafter.typhoon.repository.post;

import static com.codecrafter.typhoon.domain.entity.QPost.*;
import static com.codecrafter.typhoon.domain.entity.QPostImage.*;
import static com.codecrafter.typhoon.domain.entity.QPostViewCount.*;
import static com.querydsl.core.types.Projections.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.codecrafter.typhoon.domain.response.SimplePostDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Long getTotalPostViewCount(long postId) {
		Long count = queryFactory
			.select(postViewCount.viewCount.sum())
			.from(postViewCount)
			.where(postViewCount.postId.eq(postId))
			.groupBy(postViewCount.postId)
			.fetchOne();
		return count == null ? 0 : count;
	}

	@Override
	public List<SimplePostDto> getSimplePostDtoList(List<Long> postIdList) {
		List<SimplePostDto> simplePostDtoList = queryFactory
			.select(constructor(SimplePostDto.class,
				post.id,
				postImage.imagePath,
				post.price

			)).from(post)
			.leftJoin(postImage)
			.on(post.id.eq(postImage.post.id))
			.on(postImage.isThumbnail.eq(true))
			.where(post.id.in(postIdList))
			.limit(100)
			.fetch();

		Map<Long, SimplePostDto> collecMap = simplePostDtoList.stream()
			.collect(Collectors.toMap(SimplePostDto::getId, (v) -> v, (a, b) -> a));

		// 정렬
		return postIdList.stream()
			.map(collecMap::get)
			.toList();
	}

	// @Override
	// public Slice<Post> getPostList(Pageable pageable) {
	// 	List<Post> result = queryFactory
	// 		.selectFrom(post)
	// 		.leftJoin(post.member, member)
	// 		.offset(pageable.getOffset())
	// 		.limit(pageable.getPageSize() + 1)
	// 		.orderBy(pageable.getSort())
	// 		.fetch();
	// 	boolean hasNext = false;
	// 	if (result.size() > pageable.getPageSize()) {
	// 		hasNext = true;
	// 		result.remove(result.size() - 1);
	// 	}
	//
	// 	return new SliceImpl<>(result, pageable, hasNext);
	// }
}
