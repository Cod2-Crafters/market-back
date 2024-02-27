package com.codecrafter.typhoon.domain.response;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class CategoryCountResponse {

	private Long id;

	private String name;

	private long count;

	@JsonInclude(Include.NON_EMPTY)
	private Long parentId;

	@JsonInclude(Include.NON_EMPTY)
	private List<CategoryCountResponse> child = new ArrayList<>();

	public void setChild(List<CategoryCountResponse> child) {
		if (child == null)
			return;
		this.child = child;
		this.count = child.stream()
			.mapToLong(CategoryCountResponse::getCount)
			.sum();
	}

	public Long getParentId() {
		return parentId == null ? -1L : parentId;
	}

	public CategoryCountResponse(Long id, String name, Long count, Long parentId) {
		this.id = id;
		this.name = name;
		this.count = count;
		this.parentId = parentId;
	}
}
