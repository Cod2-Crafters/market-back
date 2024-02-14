package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	private Member commenter;

	@ManyToOne(fetch = LAZY)
	private Post post;

	private String content;

	public void setPost(Post post) {
		this.post = post;
	}

	@Builder
	public Comment(Member commenter, Post post, String content) {
		this.commenter = commenter;
		this.post = post;
		this.content = content;
	}
}
