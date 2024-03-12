package com.codecrafter.typhoon.service;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.codecrafter.typhoon.repository.PostViewCountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * redis관련
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisService {

	private static final String POST_VIEW_COUNT = "post:view:count:";

	private final PostViewCountRepository postViewCountRepository;

	private final RedisTemplate<String, String> redisTemplate;

	public long incrementPostViewCount(Long postId) {
		Long increment = redisTemplate.opsForValue().increment(POST_VIEW_COUNT + postId);
		return increment == null ? 0 : increment;
	}

	@Scheduled(cron = "0 0 0 * * *") //00시
	public void resetPostViewCount() {
		Set<String> keys = redisTemplate.keys(POST_VIEW_COUNT + "*");
		if (keys == null) {
			log.info("no keys ㅠㅠ	");
			return;
		}

		//TODO:  00시에 redis에 저장된 조회수를 DB에 저장하고 redis의 조회수를 초기화하는 로직

	}

}
