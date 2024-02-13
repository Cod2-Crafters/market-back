package com.codecrafter.typhoon.domain.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Comment;

import com.codecrafter.typhoon.domain.enumeration.PostStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Enumerated
	@Comment("판매상태")
	private PostStatus status = PostStatus.ON_SALE;

	private Boolean dltYn = false;

	/** Post에 포함되어있는 댓글들*/
	@OneToMany(mappedBy = "post")
	private List<com.codecrafter.typhoon.domain.entity.Comment> comments = new ArrayList<>();

	/**
	 * Post에서 Comments를 추가하기 위한 양방향 메서드
	 *
	 * @param comment 추가할코멘트
	 */
	public void addComments(com.codecrafter.typhoon.domain.entity.Comment comment) {
		this.comments.add(comment);
		comment.setPost(this);
	}

	@OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
	private List<PostHashtag> hashtags = new ArrayList<>();

	public void addHashtag(Hashtag hashtag) {
		//TODO
	}

	@Builder
	public Post(Member member, String title, String content, PostStatus status) {
		this.member = member;
		this.title = title;
		this.content = content;
		this.status = status;
	}
}

