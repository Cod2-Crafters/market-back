package com.codecrafter.typhoon.controller.member;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.config.UserPrincipal;
import com.codecrafter.typhoon.config.resolver.CurrentMember;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.request.RefreshTokenRequest;
import com.codecrafter.typhoon.domain.request.SignupRequest;
import com.codecrafter.typhoon.domain.request.member.UpdateMemberRequest;
import com.codecrafter.typhoon.domain.response.TokenResponse;
import com.codecrafter.typhoon.domain.response.member.MyInfoResponse;
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
	public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		String refreshToken = refreshTokenRequest.refreshToken();
		// String refreshToken = req.get("refreshToken");
		if (refreshToken == null || refreshToken.isBlank()) {
			throw new NoRefreshTokenException();
		}

		try {
			Claims claims = jwtService.getClaims(refreshToken);
			Member member = authService.findById(Long.parseLong(claims.getSubject()));
			String accessToken = jwtService.createAccessToken(new UserPrincipal(member));
			TokenResponse tokenResponse = TokenResponse.builder().
				accessToken(accessToken)
				.build();
			return ResponseEntity.status(OK).body(tokenResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new NoRefreshTokenException("TOKEN NOT VALID!!!");
		}

	}

	/**
	 * 내정보 조회
	 *
	 * @return MyInfoResponse 내정보(상세)
	 */
	@GetMapping("/myinfo")
	public ResponseEntity<MyInfoResponse> getMyInfo(@CurrentMember Member me) {
		MyInfoResponse myInfoResponse = new MyInfoResponse(me);
		return ResponseEntity.ok(myInfoResponse);
	}

	@PatchMapping("/myinfo")
	public ResponseEntity<Void> patchMyInfo(@RequestBody UpdateMemberRequest updateMemberRequest,
		@CurrentMember Member me) {
		authService.upDateMember(updateMemberRequest, me);
		return ResponseEntity.noContent().build();
	}

}
