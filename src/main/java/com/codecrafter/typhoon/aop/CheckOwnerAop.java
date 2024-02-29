package com.codecrafter.typhoon.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.codecrafter.typhoon.config.MockPrincipal;
import com.codecrafter.typhoon.domain.entity.Post;
import com.codecrafter.typhoon.exception.NoPostException;
import com.codecrafter.typhoon.repository.post.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 권한체크 aop
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CheckOwnerAop {

	private final PostRepository postRepository;

	@Before("@annotation(CheckOwner) && args(postId,..)")
	public void checkPostOwner(Long postId) {
		log.info("test");
		MockPrincipal principal = (MockPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Post post = postRepository.findById(postId).orElseThrow(NoPostException::new);
		if (!post.getMember().getId().equals(principal.getId())) {
			throw new AccessDeniedException("권한이 없다고");
		}

	}

}
