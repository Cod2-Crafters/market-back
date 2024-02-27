package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Category {

	@Id
	private Long id;

	@Column(nullable = false)
	@Comment("카테고리영")
	private String name;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(nullable = true, name = "parent_id")
	private Category parent;

	@CreationTimestamp
	@Comment("카테고리생성일자")
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "parent", cascade = ALL)
	private Set<Category> childList = new HashSet<>();

	@OneToMany(mappedBy = "category")
	private List<Post> postList = new ArrayList<>();

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public void addChild(Category child) {
		this.childList.add(child);
		child.setParent(this);
	}

	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
