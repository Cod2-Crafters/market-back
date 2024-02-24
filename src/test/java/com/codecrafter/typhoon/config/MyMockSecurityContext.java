package com.codecrafter.typhoon.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyMockSecurityContext implements WithSecurityContextFactory<MyMockUser> {

	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public SecurityContext createSecurityContext(MyMockUser annotation) {
		Member member = Member.builder()
			.email(annotation.email())
			.shopName(annotation.shopName())
			.realName("testrealName")
			.phone("111-111-1111")
			.build();
		memberRepository.save(member);

		MockPrincipal mockPrincipal = new MockPrincipal(member.getId());

		UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(
			mockPrincipal, null, mockPrincipal.getAuthorities()
		);

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authenticated);
		return context;

	}
}
