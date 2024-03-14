package com.codecrafter.typhoon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codecrafter.typhoon.service.RedisService;

@SpringBootTest
public class MyTest {

	@Autowired
	private RedisService redisService;

	@Test
	void test() {
		redisService.persistAllDailyPostViewCountToDB();
	}

}
