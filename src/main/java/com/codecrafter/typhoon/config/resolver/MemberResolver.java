package com.codecrafter.typhoon.config.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.codecrafter.typhoon.config.MockPrincipal;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.exception.NoMemberException;
import com.codecrafter.typhoon.repository.member.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemberResolver implements HandlerMethodArgumentResolver {

	private final MemberRepository memberRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(CurrentMember.class) != null &&
			parameter.getParameterType().equals(Member.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		log.info("test");
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			throw new NoMemberException();
		}
		MockPrincipal principal = (MockPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Member member = memberRepository.findById(principal.getId()).orElseThrow(NoMemberException::new);
		return member;
	}

	public MemberResolver(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

}
