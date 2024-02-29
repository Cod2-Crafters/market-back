package com.codecrafter.typhoon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * 테스트용
 */
@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class TestController {

	@GetMapping("/")
	public String Hello(@RequestParam(required = false) String err) {
		if (err == null) {
			return """
				***********
				HELLO WORLD
				***********
				""";
		}
		throw new RuntimeException();

	}

	/**
	 * 로그인 테스트
	 */
	@GetMapping("/logintest")
	public String authTest() {
		return "이 문구는, 로그인한 유저만 볼 수 있음!!";
	}

}
