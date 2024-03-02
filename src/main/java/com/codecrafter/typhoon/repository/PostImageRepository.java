package com.codecrafter.typhoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecrafter.typhoon.domain.entity.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
