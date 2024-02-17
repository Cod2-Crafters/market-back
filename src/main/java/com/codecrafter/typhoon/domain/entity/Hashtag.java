package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

	@CreationTimestamp
	@Comment("생성일자")
	private LocalDateTime createdAt;
}
