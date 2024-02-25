package com.codecrafter.typhoon.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.codecrafter.typhoon.domain.response.CategoryCountResponse;
import com.codecrafter.typhoon.repository.category.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepository categoryRepository;

	// public List<?> getAll() {
	// 	List<Category> categories = categoryRepository.getParentCategoriesWithChildren();
	// 	return categories;
	// }

	public List<CategoryCountResponse> getAllWithPostCount() {
		List<CategoryCountResponse> categoryCountList = categoryRepository.getAllCategoryWithCount();
		Map<Long, List<CategoryCountResponse>> categoryMap = categoryCountList.stream()
			.collect(groupingBy(CategoryCountResponse::getParentId));

		List<CategoryCountResponse> parents = categoryMap.get(-1L); //부모카테고리
		parents
			.forEach(p -> p.setChild(categoryMap.get(p.getId())));
		return parents;
	}
}
