package com.codecrafter.typhoon.domain.entity;

import static lombok.AccessLevel.*;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.codecrafter.typhoon.domain.enumeration.LoginType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString //실제론 지울거임
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false,unique = true)
	@Comment("이메일")
	private String email;

	@Column(nullable = false)
	@Comment("비밀번호")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false) @Comment("로그인타입4가지")
	private LoginType loginType = LoginType.BASIC;

	@Column(nullable = false, unique = true) @Comment("상점명 (젤많이쓸거)")
	private String shopName;

	@Column(nullable = false) @Comment("상점 설명")
	private String description;

	@Comment("상점 로고")
	private String logoPath;

	@Column(nullable = false)
	private String realName;

	@Comment("핸드폰번호")
	private String phone;

	@CreationTimestamp
	@Comment("생성일자")
	private String createdAt;

	@UpdateTimestamp
	@Comment("마지막수정일")
	private String modifiedAt;

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public void encodePassword(String encodedPassword) {
		this.password = encodedPassword;
	}

	@Builder
	public Member(Long id, String email, String password, LoginType loginType, String shopName, String description,
		String logoPath, String realName, String phone) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.loginType = loginType;
		this.shopName = shopName;
		this.description = description;
		this.logoPath = logoPath;
		this.realName = realName;
		this.phone = phone;

	}
}
