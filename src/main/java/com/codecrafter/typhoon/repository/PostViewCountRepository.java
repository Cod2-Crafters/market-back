package com.codecrafter.typhoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecrafter.typhoon.domain.entity.PostViewCount;

public interface PostViewCountRepository extends JpaRepository<PostViewCount, Long> {
}
