package com.codecrafter.typhoon.repository.post;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	@Query(value = """
		select p from Post p
			inner join fetch p.postHashtagList ph
			inner join  fetch ph.hashtag h
			 where p.id = :postId
		""")
	Optional<Post> findPostByIdWithHashTags(@Param("postId") Long postId);

}
