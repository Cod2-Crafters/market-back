package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
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
public class PostImage {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@Column(nullable = false)
	@Comment("이미지경로")
	private String imagePath;

	@ColumnDefault("false")
	@Comment("섬네일 여부")
	private boolean isThumbnail;

	public void setPost(Post post) {
		this.post = post;
	}

	public static PostImage createPostImage(String imagePath, boolean isThumbnail) {
		return new PostImage(imagePath, isThumbnail);
	}

	private PostImage(String imagePath, boolean isThumbnail) {
		this.imagePath = imagePath;
		this.isThumbnail = isThumbnail;
	}
}
