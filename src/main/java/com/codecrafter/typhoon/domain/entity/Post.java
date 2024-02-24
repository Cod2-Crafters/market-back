package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	@Column(nullable = false)
	@Comment("제목")
	private String title;

	@Column(nullable = false)
	@Comment("내용")
	private String content;

	@Enumerated(value = EnumType.STRING)
	@Comment("판매상태")
	private PostStatus status = PostStatus.ON_SALE;

	private boolean isDeleted;

	@OneToMany(mappedBy = "post", cascade = ALL)
	private List<PostHashtag> postHashtagList = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = ALL)
	private List<PostImage> postImageList = new ArrayList<>();

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
			this.postImageList.add(postImage);
			postImage.setPost(this);
		}
	}

	/**
	 * Collection
	 */
	public void addImages(Collection<? extends PostImage> postImages) {
		for (PostImage postImage : postImages) {
			this.postImageList.add(postImage);
			postImage.setPost(this);
		}
	}

	@Builder
	public Post(Member member, String title, String content) {
		this.member = member;
		this.title = title;
		this.content = content;
	}

	public String getThumbnailPath() {
		return this.postImageList
			.stream().filter(PostImage::isThumbnail)
			.map(PostImage::getImagePath)
			.findFirst()
			.orElse("이미지가 없어 ㅠㅠㅠㅠ");
	}
}

