package com.codecrafter.typhoon.controller;

import static org.springframework.data.domain.Sort.Direction.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.config.resolver.CurrentMember;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.request.PostCreateRequest;
import com.codecrafter.typhoon.domain.response.post.PostDetailResponse;
import com.codecrafter.typhoon.domain.response.post.SimplePostResponse;
import com.codecrafter.typhoon.service.FileService;
import com.codecrafter.typhoon.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/post")
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	private final FileService fileService;

	@GetMapping("/{postId}")
	public ResponseEntity<PostDetailResponse> getPostDetail(@PathVariable Long postId) {
		PostDetailResponse postDetail = postService.getPostDetail(postId);
		return ResponseEntity.ok().body(postDetail);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getPostList(@PageableDefault(size = 10, sort = "id", direction = DESC) Pageable pageable) {
		Slice<SimplePostResponse> postList = postService.getPostList(pageable);
		return ResponseEntity.ok().body(postList);
	}

	//상품등록ㅋ
	@PostMapping("/")
	public ResponseEntity<URI> createPost(@RequestBody PostCreateRequest postCreateRequest, @CurrentMember Member me) {
		Long postId = postService.createPost(postCreateRequest, me);
		URI uri = fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(postId)
			.toUri();
		return ResponseEntity.created(uri).body(uri);
	}

}
