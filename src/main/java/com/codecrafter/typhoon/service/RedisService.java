package com.codecrafter.typhoon.service;

import java.time.LocalDate;
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

	private final String POST_VISIT_IP_KEY_10m = "post:%s:visit10m:ip:%s";

	private static final String DAILY_POST_VIEW_COUNT = "daily:post:view:count:";

	private final PostViewCountRepository postViewCountRepository;

	private final StringRedisTemplate redisTemplate;

	public Long increaseDailyPostViewCount(Long postId, String clientIp) {
		if (hasNotVisitedInLast10m(postId, clientIp)) {
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

	/**
	 * 10분마다 조회수가 올라가도록
	 *
	 * @param postId   postId
	 * @param clientIp IP
	 * @return
	 */
	private boolean hasNotVisitedInLast10m(Long postId, String clientIp) {
		String key = POST_VISIT_IP_KEY_10m + postId + ":" + clientIp;
		Boolean visited = redisTemplate.opsForValue().setIfAbsent(key, "0", 10, TimeUnit.MINUTES);
		//TODO: return visited;
		return true;
	}

	/**
	 * 매 00시에 돌면서, 전날의 Redis에 있던 데이터를 DB에 넣고, zset 초기화화
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void persistAllDailyPostViewCountToDB() {
		log.warn("DAILY PERSIST START");
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		Set<ZSetOperations.TypedTuple<String>> viewCounts = zSetOperations.rangeWithScores(DAILY_POST_VIEW_COUNT, 0,
			-1);

		LocalDate yesterday = LocalDate.now().minusDays(1);

		if (viewCounts == null)
			return;
		int successCnt = 0;
		for (ZSetOperations.TypedTuple<String> viewCount : viewCounts) {
			try {
				long postId = Long.parseLong(viewCount.getValue());
				long count = viewCount.getScore().longValue();

				PostViewCount postViewCount = PostViewCount.of(postId, count, yesterday);
				postViewCountRepository.save(postViewCount);
				successCnt++;
			} catch (Exception e) {
				log.error("문제발생", e);
			}
		}

		redisTemplate.delete(DAILY_POST_VIEW_COUNT);
		log.warn("DAILY PERSIST END, SUCCESS COUNT={}", successCnt);
		//TODO Zset 초기화, 조회수 상품에 추가 등등
	}

}
