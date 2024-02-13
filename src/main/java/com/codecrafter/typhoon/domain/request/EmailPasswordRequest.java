package com.codecrafter.typhoon.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailPasswordRequest {

	@NotEmpty
	@Email
	private final String email;

	@NotEmpty
	private final String password;

	@Builder
	public EmailPasswordRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
