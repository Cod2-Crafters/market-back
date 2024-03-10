package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	private Integer rating;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "reviewer_id", nullable = false)
	private Member reviewer;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "reviewee_id", nullable = false)
	private Member reviewee;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean isDeleted;
}
