package com.codecrafter.typhoon.domain.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

	private final int status;
	private final String message;

	@Builder
	public ErrorResponse(int code, String message) {
		this.status = code;
		this.message = message;
	}

}
