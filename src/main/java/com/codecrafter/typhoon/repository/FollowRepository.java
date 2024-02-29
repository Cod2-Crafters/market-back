package com.codecrafter.typhoon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codecrafter.typhoon.domain.entity.Follow;
import com.codecrafter.typhoon.domain.entity.Member;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	@EntityGraph(attributePaths = {"following"})
	List<Follow> findByFollowerId(Long followId);

	@EntityGraph(attributePaths = {"follower"})
	List<Follow> findByFollowingId(Long followingId);

	boolean existsByFollowerAndFollowing(Member follower, Member following);

	Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
