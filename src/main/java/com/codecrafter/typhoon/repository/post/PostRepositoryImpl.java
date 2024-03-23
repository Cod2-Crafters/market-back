package com.codecrafter.typhoon.repository.post;

import static com.codecrafter.typhoon.domain.entity.QHashtag.*;
import static com.codecrafter.typhoon.domain.entity.QPost.*;
import static com.codecrafter.typhoon.domain.entity.QPostHashtag.*;
import static com.codecrafter.typhoon.domain.entity.QPostImage.*;
import static com.codecrafter.typhoon.domain.entity.QPostViewCount.*;
import static com.querydsl.core.types.Order.*;
import static com.querydsl.core.types.Projections.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.codecrafter.typhoon.CustomUtils;
import com.codecrafter.typhoon.domain.SearchCondition;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.domain.enumeration.PostStatus;
import com.codecrafter.typhoon.domain.response.SimplePostDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
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

	@Override
	public Slice<Post> search(SearchCondition searchCondition, Pageable pageable) {

		BooleanBuilder builder = searchConditionBuilder(searchCondition);
		List<OrderSpecifier<?>> orderSpecifierList = getPostOrderSpecifier(pageable);

		boolean hasNext = false;

		List<Post> fetch = queryFactory
			.selectFrom(post)
			.where(builder)
			.orderBy(orderSpecifierList.toArray(new OrderSpecifier[0]))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();
		if (fetch.size() > pageable.getPageSize()) {
			hasNext = true;
			fetch = fetch.subList(0, pageable.getPageSize());
		}
		return new SliceImpl<>(fetch, pageable, hasNext);
	}

	private List<OrderSpecifier<?>> getPostOrderSpecifier(Pageable pageable) {
		List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();

		Sort sort = pageable.getSort();

		for (Sort.Order order : sort) {
			PathBuilder<?> entityPath = new PathBuilder<>(Post.class, "post");
			orderSpecifierList.add(new OrderSpecifier(
				order.isAscending() ? ASC : DESC,
				entityPath.get(order.getProperty())
			));
		}
		return orderSpecifierList;
	}

	private BooleanBuilder searchConditionBuilder(SearchCondition searchCondition) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(postTitleLike(searchCondition.postTitle()));
		builder.and(postStatusEq(searchCondition.postStatus()));
		builder.and(minPriceGoe(searchCondition.minPrice()));
		builder.and(maxPriceLoe(searchCondition.maxPrice()));
		builder.and(minRegistrationTimeGoe(searchCondition.minCreatedAt()));
		builder.and(maxRegistrationTimeLoe(searchCondition.maxCreatedAt()));
		builder.and(shopIdEq(searchCondition.shopId()));
		builder.and(shopNameEq(searchCondition.shopName()));
		builder.and(categoryIdEq(searchCondition.categoryId()));
		builder.and(tagNameLike(searchCondition.tagName()));
		return builder;
	}

	private BooleanExpression postIdEq(Long postId) {
		return postId != null ? post.id.eq(postId) : null;
	}

	private BooleanExpression postTitleLike(String postTitle) {
		return StringUtils.hasText(postTitle) ? post.title.like("%" + postTitle + "%") : null;
	}

	private BooleanExpression postStatusEq(PostStatus postStatus) {
		return postStatus != null ? post.status.eq(postStatus) : null;
	}

	private BooleanExpression minPriceGoe(Long minPrice) {
		return minPrice != null ? post.price.goe(minPrice) : null;
	}

	private BooleanExpression maxPriceLoe(Long maxPrice) {
		return maxPrice != null ? post.price.loe(maxPrice) : null;
	}

	private BooleanExpression minRegistrationTimeGoe(LocalDateTime minRegistrationTime) {
		return minRegistrationTime != null ? post.createdAt.goe(minRegistrationTime) : null;
	}

	private BooleanExpression maxRegistrationTimeLoe(LocalDateTime maxRegistrationTime) {
		return maxRegistrationTime != null ? post.createdAt.loe(maxRegistrationTime) : null;
	}

	private BooleanExpression shopIdEq(Long shopId) {
		return shopId != null ? post.member.id.eq(shopId) : null;
	}

	private BooleanExpression shopNameEq(String shopName) {
		return StringUtils.hasText(shopName) ? post.member.shopName.eq(shopName) : null;
	}

	private BooleanExpression categoryIdEq(Long categoryId) {
		return categoryId != null ? post.category.id.eq(categoryId) : null;
	}

	private BooleanExpression tagNameLike(String tagName) {
		if (!StringUtils.hasText(tagName)) {
			return null;
		}
		BooleanExpression combined = hashtag.tagName.contains(tagName)
			.or(hashtag.tagName.contains(CustomUtils.getChosungString(tagName)));

		return post.postHashtagList.any().hashtag.in(
			JPAExpressions
				.select(postHashtag.hashtag)
				.from(postHashtag)
				.join(postHashtag.hashtag, hashtag)
				.where(combined)
		);

	}

}
