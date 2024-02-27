package com.codecrafter.typhoon.repository.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codecrafter.typhoon.domain.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {

	@Query("""
		select c from Category c
		left join fetch c.childList
		where c.parent is null
			""")
	List<Category> getParentCategoriesWithChildren();
}
