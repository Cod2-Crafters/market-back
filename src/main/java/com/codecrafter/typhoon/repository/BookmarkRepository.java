package com.codecrafter.typhoon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecrafter.typhoon.domain.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

	Optional<Bookmark> findByMemberIdAndPostId(long memberID, long postId);

}
