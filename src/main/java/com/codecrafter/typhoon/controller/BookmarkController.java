package com.codecrafter.typhoon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.config.MockPrincipal;
import com.codecrafter.typhoon.config.resolver.CurrentMember;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.response.Boomark.BookmarkResponse;
import com.codecrafter.typhoon.service.BookmarkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

	private final BookmarkService bookmarkService;

	@GetMapping("/me")
	public ResponseEntity<?> myBookmark(@AuthenticationPrincipal MockPrincipal principal) {
		List<BookmarkResponse> myBookmark = bookmarkService.getMyBookmark(principal.getId());
		return ResponseEntity.ok(myBookmark);
	}

	// 북마크 등록
	@PostMapping("/{postId}")
	public ResponseEntity<Void> addBookmark(@PathVariable Long postId, @CurrentMember Member me) {
		bookmarkService.addBookmark(me, postId);
		return ResponseEntity.status(201).build();
	}

	// 북마크 취소
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> removeBookmark(@PathVariable Long postId, @CurrentMember Member me) {
		bookmarkService.deleteBookmark(me, postId);
		return ResponseEntity.ok().build();
	}

}
