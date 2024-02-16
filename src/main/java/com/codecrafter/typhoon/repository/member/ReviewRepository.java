package com.codecrafter.typhoon.repository.member;

import com.codecrafter.typhoon.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMemberId(Long memberId);

}