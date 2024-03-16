package com.codecrafter.typhoon.controller.member;

import static org.springframework.http.HttpStatus.*;

import io.swagger.v3.oas.annotations.Operation;

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

	@Operation(summary = "회원가입",
		description = """
			★본인인증 회원가입
			""")
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody @Valid SignupRequest signupRequest) {
		log.info("signupRequest={}", signupRequest);
		Member member = authService.sinUp(signupRequest);
		return ResponseEntity.status(CREATED).body(member.toString());
	}

	@Operation(summary = "이메일 중복체크",
		description = """
			★회원가입시 이메일 중복 검사
			""")
	@GetMapping("/email-check")
	public void checkEmailDuplication(@RequestParam("value") String email) {
		authService.existsByEmail(email);
	}

	@Operation(summary = "상점명 중복체크",
		description = """
			★회원가입시 상점이름 중복 검사
			""")
	@GetMapping("/shopname-check")
	public void checkShopNameDuplication(@RequestParam("value") String email) {
		authService.existsByEmail(email);
	}

	@Operation(summary = "세션 연장",
		description = """
			★인증정보를 바탕으로 신규 토큰 발급
			""")
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

	@Operation(summary = "상점 정보 조회",
		description = """
			★개인 상점 조회</br>
			MemberId = 숫자</br>
			{host}/api/auth/myinfo
			""")
	@GetMapping("/myinfo")
	public ResponseEntity<MyInfoResponse> getMyInfo(@CurrentMember Member me) {
		MyInfoResponse myInfoResponse = new MyInfoResponse(me);
		return ResponseEntity.ok(myInfoResponse);
	}

	@Operation(summary = "상점 정보 수정",
		description = """
			★개인 상점 수정</br>
			{
			  "shopName": "지진마켓",</br>
			  "description": "태풍마켓에서 변경했습니다",</br>
			  "phone": "01012341234"</br>
			}
			""")
	@PatchMapping("/myinfo")
	public ResponseEntity<Void> patchMyInfo(@RequestBody UpdateMemberRequest updateMemberRequest,
		@CurrentMember Member me) {
		authService.upDateMember(updateMemberRequest, me);
		return ResponseEntity.noContent().build();
	}

}
