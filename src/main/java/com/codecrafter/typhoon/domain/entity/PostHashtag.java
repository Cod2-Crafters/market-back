package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostHashtag {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "POST_ID")
	private Post post;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "hashtag_id")
	private Hashtag hashtag;

	public PostHashtag(Post post, Hashtag hashtag) {
		this.hashtag = hashtag;
		this.post = post;
	}

	@Override
	public String toString() {
		return "PostHashtag{" +
			"id=" + id +
			", postID=" + post.getId() +
			", hashtag=" + hashtag +
			'}';
	}
}
