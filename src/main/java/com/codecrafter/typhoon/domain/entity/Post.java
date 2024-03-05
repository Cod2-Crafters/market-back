package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;

import com.codecrafter.typhoon.domain.enumeration.PostStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@JsonIgnoreProperties(value = "post")
@SQLDelete(sql = "update POST set is_deleted = true where id=?")
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(nullable = false)
	@Comment("제목")
	private String title;

	@Column(nullable = false)
	@Comment("내용")
	private String content;

	@Enumerated(value = EnumType.STRING)
	@Comment("판매상태")
	private PostStatus status = PostStatus.ON_SALE;

	@Comment("가격")
	private Integer price;

	private boolean isDeleted;

	@OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
	private Set<PostHashtag> postHashtagList = new HashSet<>();

	@OneToMany(mappedBy = "post", cascade = PERSIST, orphanRemoval = true)
	private List<PostImage> postImageList = new ArrayList<>();

	public void setCategory(Category category) {
		this.category = category;
		category.getPostList().add(this);
	}

	public void addPostHashtag(Hashtag hashtag) {
		PostHashtag postHashtag = new PostHashtag(this, hashtag);
		this.postHashtagList.add(postHashtag);
		hashtag.addPostHashtag(postHashtag);
	}

	/**
	 * varargs
	 */
	public void addImages(PostImage... postImages) {
		for (PostImage postImage : postImages) {
			if (postImage.isThumbnail()) {
				refreshThumbnail();
			}
			this.postImageList.add(postImage);
			postImage.setPost(this);
		}
	}

	/**
	 * Collection
	 */
	public void addImages(Collection<? extends PostImage> postImages) {
		for (PostImage postImage : postImages) {
			if (postImage.isThumbnail()) {
				refreshThumbnail();
			}
			this.postImageList.add(postImage);
			postImage.setPost(this);
		}
	}

	private void refreshThumbnail() {
		this.postImageList
			.forEach(p -> p.setThumbnail(false));
	}

	@Builder(builderClassName = "")
	public Post(Member member, String title, String content, Integer price) {
		this.member = member;
		this.title = title;
		this.content = content;
		this.price = price;
	}

	public String getThumbnailPath() {
		return this.postImageList
			.stream().filter(PostImage::isThumbnail)
			.map(PostImage::getImagePath)
			.findFirst()
			.orElse("이미지가 없어 ㅠㅠㅠㅠ");
	}

	public void updateTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException("TITLE cannot be empty");
		}

		this.title = title;
	}

	public void updateContent(String content) {
		if (content == null || content.isEmpty()) {
			throw new IllegalArgumentException("content cannot be empty");
		}
		this.content = content;
	}

	public void updateStatus(PostStatus postStatus) {
		if (postStatus == null) {
			throw new IllegalArgumentException("postStatus cannot be empty");
		}
		this.status = postStatus;
	}

	public void updatePrice(Integer price) {
		if (price == null) {
			throw new IllegalArgumentException("price cannot be empty");
		}
		this.price = price;
	}

}

