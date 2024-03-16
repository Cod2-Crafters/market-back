package com.codecrafter.typhoon.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.domain.response.CategoryCountResponse;
import com.codecrafter.typhoon.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

	private final CategoryService categoryService;

	// @RequestMapping("/")
	// public List<?> getAll() {
	// 	return categoryService.getAll();
	// }

	@Operation(summary = "카테고리 개수 조회",
		description = """
								★카테고리 총 개수 조회</br>
								{host}/api/category/list
			""")
	@GetMapping("/list")
	public ResponseEntity<List<CategoryCountResponse>> getAllWithCount() {
		List<CategoryCountResponse> allWithPostCount = categoryService.getAllWithPostCount();
		return ResponseEntity.ok().body(allWithPostCount);
	}

}
