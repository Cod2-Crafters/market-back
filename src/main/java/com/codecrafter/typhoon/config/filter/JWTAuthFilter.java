package com.codecrafter.typhoon.config.filter;

import static com.codecrafter.typhoon.service.JWTService.*;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codecrafter.typhoon.service.JWTService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {

	private final JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
		if (accessToken == null || !accessToken.startsWith(ACCESS_TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			accessToken = accessToken.substring(7);
			Claims claims = jwtService.getClaims(accessToken);
			UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(
				claims.getSubject(), null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (Exception e) {
			log.error(e.getMessage());
			response.addHeader("WWW-Authenticate", "Bearer error= 'not valid token'");
		}
		filterChain.doFilter(request, response);
	}
}
