package com.codecrafter.typhoon.repository.hashtag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecrafter.typhoon.domain.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findByTagName(String tagName);
}
