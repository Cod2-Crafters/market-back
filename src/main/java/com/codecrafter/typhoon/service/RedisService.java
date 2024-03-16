package com.codecrafter.typhoon.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.codecrafter.typhoon.domain.dto.ViewCountDto;
import com.codecrafter.typhoon.domain.entity.PostViewCount;
import com.codecrafter.typhoon.exception.NotExistException;
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

	/**
	 * 오늘 가장 많이 팔린 상품 n개를 가지고오기
	 *
	 * @param limit 가기져올 상위 n개
	 * @return ViewCountDto {@link ViewCountDto}
	 */
	public List<Long> getMostViewedItemsToday(long limit) {
		Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet()
			.rangeWithScores(DAILY_POST_VIEW_COUNT, 0, limit - 1);
		if (typedTuples == null) {
			throw new NotExistException("오늘 단 하나의 조회도 발생하지 않았음!!!!!");
		}
		List<Long> postIdList = typedTuples.stream()
			.mapToLong(t -> {
				try {
					return Long.parseLong(Objects.requireNonNull(t.getValue()));
				} catch (Exception e) {
					log.error("error in change to Long", e);
					return -100L; // 변환에 실패한 경우 0L을 반환합니다.
				}
			})
			.filter((v) -> v != -1L)
			.boxed().toList();
		return postIdList;
	}
}
