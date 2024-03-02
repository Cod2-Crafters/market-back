package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Hashtag {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String tagName;

	@OneToMany(mappedBy = "hashtag")
	private final Set<PostHashtag> postHashtagSet = new HashSet<>();

	@CreationTimestamp
	@Comment("생성일자")
	private LocalDateTime createdAt;

	public void addPostHashtag(PostHashtag postHashtag) {
		postHashtagSet.add(postHashtag);
	}

	public Hashtag(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "Hashtag{" +
			"id=" + id +
			", tagName='" + tagName + '\'' +
			", createdAt=" + createdAt +
			'}';
	}
}
