package com.codecrafter.typhoon.config.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.codecrafter.typhoon.domain.request.member.EmailPasswordRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailPasswordAuthFilter extends AbstractAuthenticationProcessingFilter {

	private static final String LOGIN_PATH = "/api/auth/login";

	private final ObjectMapper objectMapper;

	public EmailPasswordAuthFilter(ObjectMapper objectMapper) {
		super(LOGIN_PATH);
		this.objectMapper = objectMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException, IOException, ServletException {
		EmailPasswordRequest emailPassword = objectMapper.readValue(request.getInputStream(),
			EmailPasswordRequest.class);

		UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
			emailPassword.getEmail(), emailPassword.getPassword());
		token.setDetails(authenticationDetailsSource.buildDetails(request));
		return this.getAuthenticationManager().authenticate(token);

	}

/*	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.warn("successfulAuthentication 호출됨"); // 테스트용
		//이게있으면, handler가 발동하지 않음
	}*/

}
