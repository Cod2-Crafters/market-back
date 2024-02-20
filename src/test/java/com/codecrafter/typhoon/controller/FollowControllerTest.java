package com.codecrafter.typhoon.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.codecrafter.typhoon.config.UserPrincipal;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.repository.FollowRepository;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.service.AuthService;
import com.codecrafter.typhoon.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@AutoConfigureMockMvc
@SpringBootTest
class FollowControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AuthService authService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private FollowRepository followRepository;

	@Autowired
	private JWTService jwtService;

	private String accessToken;

	@BeforeEach
	void clean() {
		followRepository.deleteAll();
	}

	@PostConstruct
	void setup() {
		Member member1 = Member.builder()
			.email("member1@member1.com")
			.password("password")
			.shopName("testShopname1")
			.description("testDescription1")
			.realName("test1")
			.phone("010-0000-0000")
			.build();

		Member member2 = Member.builder()
			.email("member2@member2.com")
			.password("password")
			.shopName("testShopname2")
			.description("testDescription2")
			.realName("test1")
			.phone("010-0000-0000")
			.build();

		memberRepository.save(member1);
		memberRepository.save(member2);

		Member me = Member.builder()
			.email("me@me.com")
			.password("password")
			.shopName("myshopname")
			.description("testDescription2")
			.realName("test2")
			.phone("010-0000-0000")
			.build();
		memberRepository.save(me);
		accessToken = jwtService.createAccessToken(new UserPrincipal(me));

	}

	@PreDestroy
	void afterAll() {
		memberRepository.deleteAll();
	}

}