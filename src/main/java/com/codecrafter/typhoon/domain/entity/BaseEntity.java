package com.codecrafter.typhoon.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
	@CreationTimestamp
	@Comment("생성일자")
	private LocalDateTime createdAt;


	@UpdateTimestamp
	@Comment("마지막수정일")
	private LocalDateTime modifiedAt;
}

