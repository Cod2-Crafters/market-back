package com.codecrafter.typhoon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

	@RequestMapping("/list")
	public ResponseEntity<List<CategoryCountResponse>> getAllWithCount() {
		List<CategoryCountResponse> allWithPostCount = categoryService.getAllWithPostCount();
		return ResponseEntity.ok().body(allWithPostCount);
	}

}
