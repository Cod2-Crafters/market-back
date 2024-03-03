package com.codecrafter.typhoon.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codecrafter.typhoon.repository.hashtag.HashtagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class HashtagService {

	private final HashtagRepository hashtagRepository;

	public List<String> getSuggestedHashtags(String input, Pageable pageable) {
		List<String> similarTagNames = hashtagRepository.findSimilarTagNames(input, pageable);
		return similarTagNames;
	}

}
