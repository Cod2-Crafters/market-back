package com.codecrafter.typhoon.service;

import static com.codecrafter.typhoon.domain.enumeration.LoginType.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.request.SignupRequest;
import com.codecrafter.typhoon.exception.AlreadyExistException;
import com.codecrafter.typhoon.exception.NoMemberException;
import com.codecrafter.typhoon.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원가입 로직
	 *
	 * @param signupRequest 회원가입 record
	 */
	@Transactional(readOnly = false)
	public Member sinUp(SignupRequest signupRequest) {
		// if (memberRepository.existsByEmail(signupRequest.email()) || memberRepository.existsByShopName(
		// 	signupRequest.shopName())) {
		// 	throw new AlreadyExistException("이미 사용된 이름입니다, 중복검사 필요!");
		// }
		existsByEmail(signupRequest.email());
		existsByShopName(signupRequest.shopName());
		Member member = signupRequest.toEntity();
		member.setLoginType(BASIC);
		member.encodePassword(passwordEncoder.encode(member.getPassword()));
		memberRepository.save(member);
		return member;
	}

	public void existsByEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new AlreadyExistException("already exists Email!!");
		}
	}

	public void existsByShopName(String shopName) {
		if (memberRepository.existsByShopName(shopName)) {
			throw new AlreadyExistException("already exists shopName!!");
		}
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(NoMemberException::new);
	}

}
