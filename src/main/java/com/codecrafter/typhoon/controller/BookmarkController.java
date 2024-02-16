package com.codecrafter.typhoon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.config.MockPrincipal;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {
	// 북마크 등록
	@PostMapping("/{postId}")
	public ResponseEntity<?> addBookmark(@PathVariable Long postId,
		@AuthenticationPrincipal MockPrincipal mockPrincipal) {
		System.out.println("mockAuth = " + mockPrincipal);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("authentication = " + authentication);
		return ResponseEntity.ok().body("addBookmark");
	}

	// 북마크 취소
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> removeBookmark(@PathVariable Long postId) {
		return ResponseEntity.ok().body("removeBookmark");
	}

}
