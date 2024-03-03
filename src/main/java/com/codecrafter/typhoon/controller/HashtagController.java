package com.codecrafter.typhoon.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.service.HashtagService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/hashtag")
@Slf4j
@RequiredArgsConstructor
public class HashtagController {

	private final HashtagService hashtagService;

	@GetMapping("/suggested-hashtags")
	public ResponseEntity<List<String>> getSuggestedHashtags(@RequestParam String input,
		@RequestParam(defaultValue = "10") int limit) {
		PageRequest pageRequest = PageRequest.of(0, limit);
		List<String> suggestedHashtags = hashtagService.getSuggestedHashtags(input, pageRequest);
		return ResponseEntity.ok().body(suggestedHashtags);
	}

}
