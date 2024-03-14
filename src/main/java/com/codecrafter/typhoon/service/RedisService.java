package com.codecrafter.typhoon.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.codecrafter.typhoon.domain.entity.PostViewCount;
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

	private static final String DAILY_POST_VIEW_COUNT = "daily:post:view:count:";

	private final PostViewCountRepository postViewCountRepository;

	private final StringRedisTemplate redisTemplate;

	public Long increaseDailyPostViewCount(Long postId, String clientIp) {
		if (hasNotVisitedInLastHour(postId, clientIp)) {
			Double score = redisTemplate.opsForZSet()
				.incrementScore(DAILY_POST_VIEW_COUNT, String.valueOf(postId), 1);
			return score == null ? 0 : score.longValue();
		}
		return getDailyPostViewCount(postId);
	}

	public long getDailyPostViewCount(Long postId) {
		Double score = redisTemplate.opsForZSet().score(DAILY_POST_VIEW_COUNT, String.valueOf(postId));
		return score != null ? score.longValue() : 0;
	}

	private boolean hasNotVisitedInLastHour(Long postId, String clientIp) {
		String key = LAST_ONE_HOUR + postId + ":" + clientIp;
		Boolean visited = redisTemplate.opsForValue().setIfAbsent(key, "0", 1, TimeUnit.HOURS);
		return visited;  //TODO: return visited;
		// return !visited;
	}

	/**
	 * 매 00시에 돌면서, 전날의 Redis에 있던 데이터를 DB에 넣는 스케줄링링
	 */
	@Scheduled(fixedRate = 3000)
	public void persistAllDailyPostViewCountToDB() {
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		Set<ZSetOperations.TypedTuple<String>> viewCounts = zSetOperations.rangeWithScores(DAILY_POST_VIEW_COUNT, 0,
			-1);

		LocalDate yesterday = LocalDate.now().minusDays(1);

		if (viewCounts == null)
			return;

		for (ZSetOperations.TypedTuple<String> viewCount : viewCounts) {
			try {
				long postId = Long.parseLong(viewCount.getValue());
				long count = viewCount.getScore().longValue();

				PostViewCount postViewCount = PostViewCount.of(postId, count, yesterday);
				postViewCountRepository.save(postViewCount);
			} catch (Exception e) {
				log.error("문제발생", e);
			}
		}

		log.info("finished at {}", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
	}

}
