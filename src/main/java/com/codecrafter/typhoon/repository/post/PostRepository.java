package com.codecrafter.typhoon.repository.post;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.codecrafter.typhoon.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

	@EntityGraph(attributePaths = "member")
	Optional<Post> findPostWithMemberById(Long postId);

	@EntityGraph(attributePaths = "member")
	Slice<Post> findWithMemberBy(Pageable pageable);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Post", nativeQuery = true)
	void physicalDeleteForTest();
}
