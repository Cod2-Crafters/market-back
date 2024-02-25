package com.codecrafter.typhoon.repository.category;

import static com.codecrafter.typhoon.domain.entity.QCategory.*;
import static com.codecrafter.typhoon.domain.entity.QPost.*;

import java.util.List;

import com.codecrafter.typhoon.domain.response.CategoryCountResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<CategoryCountResponse> getAllCategoryWithCount() {
		List<CategoryCountResponse> list = queryFactory
			.select(Projections.constructor(CategoryCountResponse.class,
					category.id,
					category.name,
					post.count(),
					category.parent.id
				)
			).from(category)
			.leftJoin(post).on(post.category.eq(category))
			.groupBy(category.id)
			.fetch();

		return list;
	}

}
