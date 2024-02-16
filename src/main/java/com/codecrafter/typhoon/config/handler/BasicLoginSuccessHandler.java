package com.codecrafter.typhoon.config.handler;

import static com.codecrafter.typhoon.service.JWTService.*;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.codecrafter.typhoon.config.UserPrincipal;
import com.codecrafter.typhoon.domain.response.TokenResponse;
import com.codecrafter.typhoon.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BasicLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JWTService jwtService;

	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		UserPrincipal userprincipal = (UserPrincipal)(authentication.getPrincipal());
		log.info("인증성공 UserPrincipal={}", userprincipal);

		String accessToken = jwtService.createAccessToken(userprincipal);
		String refreshToken = jwtService.createRefreshToken(userprincipal);

		response.addHeader(ACCESS_TOKEN_HEADER, accessToken);
		response.addHeader(REFRESH_TOKEN_STRING, refreshToken);

		TokenResponse tokenResponse = TokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
		System.out.println("tokenResponse = " + tokenResponse);

		objectMapper.writeValue(response.getWriter(), tokenResponse);
	}
}
