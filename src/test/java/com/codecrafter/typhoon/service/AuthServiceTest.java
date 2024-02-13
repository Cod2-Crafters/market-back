package com.codecrafter.typhoon.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.request.SignupRequest;
import com.codecrafter.typhoon.exception.AlreadyExistException;
import com.codecrafter.typhoon.repository.MemberRepository;

import jakarta.persistence.EntityManager;

@SpringBootTest
class AuthServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private  AuthService authService;

	@Autowired
	private EntityManager em;

	@AfterEach
	void clean(){
		memberRepository.deleteAll();
	}

	@Test
	@Transactional
	@DisplayName("회원가입 성공")
	void test1() throws  Exception{
		//given
			SignupRequest signupRequest = new SignupRequest("abcd@abcd.com",
				"password",
				"realName",
				"shopName",
				"test",
				"010-0000-0000");
		Member member = authService.sinUp(signupRequest);

		//when
		Member findMember = authService.findById(member.getId());

		//then
		assertThat(member).isEqualTo(findMember);
	}

	@Test
	@Transactional
	@DisplayName("중복 회원가입 실패")
	void test2() throws Exception{
		//given
		SignupRequest signupRequest = new SignupRequest("abcd@abcd.com",
			"password",
			"realName",
			"shopName",
			"test",
			"010-0000-0000");
		authService.sinUp(signupRequest);
		em.flush();
		em.clear();

		//expected
		AlreadyExistException thrown = Assertions.assertThrows(AlreadyExistException.class,
			() -> authService.sinUp(signupRequest));
		System.out.println("thrown.getMessage() = " + thrown.getMessage());

	}

}