package com.codecrafter.typhoon;

import static org.assertj.core.api.Assertions.*;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@AfterEach
	void clean() {
		redisTemplate.delete(Objects.requireNonNull(redisTemplate.keys("*")));
	}

	private static final int TREAD_CNT = 1000;

	@Test
	void redisConnectionTest() {
		final String key = "a";
		final String data = "1";
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, data);
		final String s = valueOperations.get(key);
		assertThat(s).isEqualTo("1");
	}

	@Test
	void incrTest() {
		final String key = "test";
		final String value = "1";

		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		valueOperations.setIfAbsent(key, value);
		valueOperations.increment(key);
		String currentValue = valueOperations.get(key);
		assertThat(currentValue).isEqualTo("2");
	}

	@Test
	void concurrencyTest1() throws InterruptedException {
		int[] ints = new int[1];
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch countDownLatch = new CountDownLatch(TREAD_CNT);
		for (int i = 0; i < TREAD_CNT; i++) {
			executorService.submit(() -> {
				ints[0]++;
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		assertThat(ints[0]).isNotEqualTo(1000);
	}

	@Test
	void concurrencyTest2() throws InterruptedException {
		AtomicInteger value = new AtomicInteger();
		ExecutorService executorService = Executors.newFixedThreadPool(32);

		for (int i = 0; i < TREAD_CNT; i++) {
			executorService.submit(() -> {
				value.incrementAndGet();
			});
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.SECONDS);
		assertThat(value.get()).isEqualTo(1000);
	}

	@Test
	void concurrencyTest3() throws InterruptedException {
		String key = "value";
		String value = "0";
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch countDownLatch = new CountDownLatch(TREAD_CNT);
		redisTemplate.opsForValue()
			.set(key, value);
		executorService.submit(() -> {
			for (int i = 0; i < TREAD_CNT; i++) {
				redisTemplate.opsForValue()
					.increment(key);
				countDownLatch.countDown();
			}
			assertThat(redisTemplate.opsForValue()
				.get(key)).isEqualTo("1000");
		});

	}

}
