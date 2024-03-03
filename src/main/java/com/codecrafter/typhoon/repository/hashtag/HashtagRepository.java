package com.codecrafter.typhoon.repository.hashtag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codecrafter.typhoon.domain.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findByTagName(String tagName);

	@Query("""
		select h.tagName from Hashtag h
		where h.tagName like %:input%
		""")
	List<String> findSimilarTagNames(@Param("input") String input, Pageable pageable);

}
