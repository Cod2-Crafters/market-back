package com.codecrafter.typhoon.repository.member;

import static com.codecrafter.typhoon.domain.entity.QFollow.*;
import static com.codecrafter.typhoon.domain.entity.QMember.*;
import static com.codecrafter.typhoon.domain.entity.QPost.*;
import static com.codecrafter.typhoon.domain.entity.QPostImage.*;
import static com.querydsl.core.types.Projections.*;
import static com.querydsl.jpa.JPAExpressions.*;

import java.util.List;

import com.codecrafter.typhoon.domain.response.PostShopResponse;
import com.codecrafter.typhoon.domain.response.SimplePostResponse;
import com.codecrafter.typhoon.exception.NotExistException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public PostShopResponse getPostShop(Long memberId) {
		PostShopResponse postShopResponse = queryFactory
			.select(constructor(PostShopResponse.class,
				member.id,
				member.shopName,
				member.logoPath,
				select(post.count()).from(post).where(post.member.id.eq(member.id)),
				select(follow.count()).from(follow).where(follow.following.id.eq(member.id))

			))
			.from(member)
			.where(member.id.eq(memberId))
			.fetchOne();
		if (postShopResponse == null) {
			throw new NotExistException();
		}

		List<SimplePostResponse> simplePostResponseList = queryFactory.select(constructor(SimplePostResponse.class,
				post.id,
				postImage.imagePath,
				post.price
			)).from(post)
			.leftJoin(post.postImageList, postImage)
			.on(postImage.isThumbnail.eq(true))
			.where(post.member.id.eq(memberId))
			.limit(6)
			.fetch();

		postShopResponse.seTthumbnailAndPriceList(simplePostResponseList);

		return postShopResponse;
	}
}
