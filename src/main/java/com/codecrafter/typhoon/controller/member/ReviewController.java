package com.codecrafter.typhoon.controller.member;

import com.codecrafter.typhoon.domain.request.ReviewRequest;
import com.codecrafter.typhoon.domain.response.ReviewResponse;
import com.codecrafter.typhoon.repository.member.MemberRepository;
import com.codecrafter.typhoon.repository.member.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    ReviewRepository reviewRepository;

    // 후기 등록
    @PostMapping("/{memberId}")
    public ResponseEntity<?> createReview(@PathVariable Long memberId, @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok().body("createReview");
    }

    // 후기 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List> getReviews(@PathVariable Long id) {
        List reviewList = Collections.singletonList(reviewRepository.findById(id));
        return ResponseEntity.ok(reviewList);
    }

    // 후기에 대한 신고
    @PostMapping("/{memberId}/{reviewId}/report")
    public ResponseEntity<?> reportReview(@PathVariable Long memberId, @PathVariable Long reviewId) {
        return ResponseEntity.ok().body("reportReview");
    }

}
