package com.codecrafter.typhoon.repository.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecrafter.typhoon.domain.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
