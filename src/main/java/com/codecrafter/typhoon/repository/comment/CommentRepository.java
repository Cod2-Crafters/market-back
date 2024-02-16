package com.codecrafter.typhoon.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecrafter.typhoon.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
