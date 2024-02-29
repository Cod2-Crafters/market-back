package com.codecrafter.typhoon.repository;

import static com.codecrafter.typhoon.domain.entity.QBookmark.*;
import static com.codecrafter.typhoon.domain.entity.QPost.*;
import static com.codecrafter.typhoon.domain.entity.QPostImage.*;

import java.util.List;

import com.codecrafter.typhoon.domain.response.Boomark.BookmarkResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public List<BookmarkResponse> getMyBookmarkList(Long memberId) {
		List<BookmarkResponse> bookmarkResponseList = queryFactory
			.select(Projections.constructor(BookmarkResponse.class,
				bookmark.id, post.id, post.title, postImage
			))
			.from(bookmark)
			.join(bookmark.post, post)
			.leftJoin(post, postImage.post).on(postImage.isThumbnail.eq(true))
			.where(bookmark.member.id.eq(memberId))
			.limit(100)
			.fetch();
		return bookmarkResponseList; //TODO N+1예상
	}

}
