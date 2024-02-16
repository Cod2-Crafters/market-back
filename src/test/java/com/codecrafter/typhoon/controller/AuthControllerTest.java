package com.codecrafter.typhoon.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.codecrafter.typhoon.config.Userprincipal;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.request.EmailPasswordRequest;
import com.codecrafter.typhoon.domain.request.SignupRequest;
import com.codecrafter.typhoon.domain.response.TokenResponse;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.service.AuthService;
import com.codecrafter.typhoon.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

	@Autowired
	public MockMvc mockMvc;

	@Autowired
	private AuthService authService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private JWTService jwtService;

	SignupRequest createMockSignup() {
		SignupRequest signupRequest = new SignupRequest("abcd@abcd.com",
			"password",
			"realName",
			"shopName",
			"test",
			"010-0000-0000");
		return signupRequest;
	}

	@AfterEach
	void clean() {
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("기본 회원 가입 테스트")
	void test1_1() throws Exception {
		//given
		SignupRequest mockSignup = createMockSignup();

		String json = objectMapper.writeValueAsString(mockSignup);
		//expect
		mockMvc.perform(post("/api/auth/signup")
				.content(json)
				.contentType(APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andDo(print());

		assertThat(memberRepository.existsByShopName(mockSignup.shopName())).isTrue();
		assertThat(memberRepository.existsByEmail(mockSignup.email())).isTrue();

		mockMvc.perform(post("/api/auth/signup") //중복
				.content(json)
				.contentType(APPLICATION_JSON))
			.andExpect(status().isConflict());
	}

	@Test
	@DisplayName("로그인(토큰발급테스트)")
	void test2_1() throws Exception {
		//given
		SignupRequest mockSignup = createMockSignup();
		authService.sinUp(mockSignup); //회원가입
		EmailPasswordRequest request = EmailPasswordRequest.builder()
			.email(mockSignup.email())
			.password(mockSignup.password())
			.build();

		String json = objectMapper.writeValueAsString(request);
		//expect
		MvcResult result = mockMvc.perform(post("/api/auth/login")
				.content(json)
				.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.accessToken").isNotEmpty())
			.andExpect(jsonPath("$.refreshToken").isNotEmpty())
			.andDo(print())
			.andReturn();

		String body = result.getResponse().getContentAsString();
		TokenResponse tokenResponse = objectMapper.readValue(body, TokenResponse.class);
		String accessToken = tokenResponse.getAccessToken();
		String refreshToken = tokenResponse.getRefreshToken();
		System.out.println("jwtService.getClaims(accessToken) = " + jwtService.getClaims(accessToken));
		System.out.println("jwtService.getClaims(refreshToken) = " + jwtService.getClaims(refreshToken));
	}

	@Test
	@DisplayName("로그인(실패)")
	void test2_2() throws Exception {
		//given
		SignupRequest mockSignup = createMockSignup();
		authService.sinUp(mockSignup); //회원가입

		EmailPasswordRequest request = EmailPasswordRequest.builder()
			.email(mockSignup.email())
			.password(mockSignup.password() + "awalkauh")
			.build();

		String json = objectMapper.writeValueAsString(request);

		mockMvc.perform(post("/api/auth/login")
				.content(json)
				.contentType(APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andDo(print());

	}

	@Test
	@DisplayName("토큰 권한인증테스트")
	void test3() throws Exception {
		//given
		SignupRequest mockSignup = createMockSignup();
		Member member = memberRepository.save(mockSignup.toEntity());
		String accessToken = jwtService.createRefreshToken(new Userprincipal(member));
		System.out.println("accessToken = " + accessToken);

		//expect
		mockMvc.perform(get("/logintest")
				.header(JWTService.ACCESS_TOKEN_HEADER, JWTService.ACCESS_TOKEN_PREFIX + accessToken).accept(TEXT_PLAIN))
			.andExpect(status().isOk());
		mockMvc.perform(get("/logintest"))
			.andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("refreshToken 발급 테스트")
	void test4() throws Exception {
		//given
		SignupRequest mockSignup = createMockSignup();
		Member member = memberRepository.save(mockSignup.toEntity());
		String refreshToken = jwtService.createRefreshToken(new Userprincipal(member));

		String json = "{\"refreshToken\" : \"" + refreshToken + "\"}";

		mockMvc.perform(post("/api/auth/refresh")
				.content(json)
				.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.accessToken").isNotEmpty())
			.andDo(print());
	}

}