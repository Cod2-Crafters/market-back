package com.codecrafter.typhoon.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.config.Userprincipal;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.request.SignupRequest;
import com.codecrafter.typhoon.domain.response.TokenResponse;
import com.codecrafter.typhoon.exception.NoRefreshTokenException;
import com.codecrafter.typhoon.service.AuthService;
import com.codecrafter.typhoon.service.JWTService;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	private final JWTService jwtService;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody @Valid SignupRequest signupRequest) {
		log.info("signupRequest={}", signupRequest);
		Member member = authService.sinUp(signupRequest);
		return ResponseEntity.status(CREATED).body(member.toString());
	}

	@GetMapping("/email-check")
	public void checkEmailDuplication(@RequestParam("value") String email) {
		authService.existsByEmail(email);
	}

	@GetMapping("/shopname-check")
	public void checkShopNameDuplication(@RequestParam("value") String email) {
		authService.existsByEmail(email);
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> req) {
		String refreshToken = req.get("refreshToken");
		if (refreshToken == null || refreshToken.isBlank()) {
			throw new NoRefreshTokenException();
		}

		try {
			Claims claims = jwtService.getClaims(refreshToken);
			Member member = authService.findById(Long.parseLong(claims.getSubject()));
			String accessToken = jwtService.createAccessToken(new Userprincipal(member));
			TokenResponse tokenResponse = TokenResponse.builder().
				accessToken(accessToken)
				.build();
			return ResponseEntity.status(OK).body(tokenResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new NoRefreshTokenException("TOKEN NOT VALID!!!");
		}

	}

}
