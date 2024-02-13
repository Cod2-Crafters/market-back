package com.codecrafter.typhoon.domain.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ValidationErrorResponse {

	private final int status;
	private final String message;
	private final Map<String, String> validation;

	@Builder
	public ValidationErrorResponse(int code, String message, Map<String, String> validation) {
		this.status = code;
		this.message = message;
		this.validation = validation != null ? validation : new HashMap<>();
	}

}
