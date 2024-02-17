package com.codecrafter.typhoon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.domain.entity.Member;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {
	// 북마크 등록
	@PostMapping("/{postId}")
	public ResponseEntity<?> addBookmark(@PathVariable Long postId, Member member) {
		System.out.println("member = " + member);
		System.out.println(
			"SecurityContextHolder.getContext().getAuthentication() = " + SecurityContextHolder.getContext()
				.getAuthentication());
		return ResponseEntity.ok().body("addBookmark");
	}

	// 북마크 취소
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> removeBookmark(@PathVariable Long postId) {
		return ResponseEntity.ok().body("removeBookmark");
	}

}
