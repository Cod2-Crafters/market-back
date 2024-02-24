package com.codecrafter.typhoon.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

	private final JPAQueryFactory queryFactory;

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
