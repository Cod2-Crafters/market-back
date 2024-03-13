package com.codecrafter.typhoon.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.codecrafter.typhoon.repository.PostViewCountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * redis관련
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

	private final String LAST_ONE_HOUR = "last:one:hour:";

	private static final String POST_VIEW_COUNT_DAILY = "daily:post:view:count:";

	private final PostViewCountRepository postViewCountRepository;

	private final StringRedisTemplate redisTemplate;

	public Long increaseDailyPostViewCount(Long postId, String clientIp) {
		if (hasNotVisitedInLastHour(postId, clientIp)) {
			Double score = redisTemplate.opsForZSet()
				.incrementScore(POST_VIEW_COUNT_DAILY, String.valueOf(postId), 1);
			return score == null ? 0 : score.longValue();
		}
		return getPostViewCountDaily(postId);
	}

	public long getPostViewCountDaily(Long postId) {
		Double score = redisTemplate.opsForZSet().score(POST_VIEW_COUNT_DAILY, String.valueOf(postId));
		return score != null ? score.longValue() : 0;
	}

	private boolean hasNotVisitedInLastHour(Long postId, String clientIp) {
		String key = LAST_ONE_HOUR + postId + ":" + clientIp;
		Boolean visited = redisTemplate.opsForValue().setIfAbsent(key, "0", 1, TimeUnit.HOURS);
		return visited;  //TODO: return visited;
		// return !visited;
	}

}
