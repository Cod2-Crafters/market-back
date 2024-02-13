package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

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
public class Follow {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "following_id")
	@Comment("팔로잉당하는사람")
	private Member following;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "follower_id")
	@Comment("팔로우하는 사람")
	private Member follower;

	@CreationTimestamp
	@Comment("생성일자")
	private String createdAt;

}
