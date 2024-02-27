package com.codecrafter.typhoon.repository.category;

import java.util.List;

import com.codecrafter.typhoon.domain.response.CategoryCountResponse;

public interface CategoryRepositoryCustom {

	List<CategoryCountResponse> getAllCategoryWithCount();
}
