package com.codecrafter.typhoon.controller;

import static org.springframework.data.domain.Sort.Direction.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.*;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.aop.CheckOwner;
import com.codecrafter.typhoon.config.resolver.CurrentMember;
import com.codecrafter.typhoon.domain.entity.Member;
import com.codecrafter.typhoon.domain.request.HashtagsRequest;
import com.codecrafter.typhoon.domain.request.post.ImageRequest;
import com.codecrafter.typhoon.domain.request.post.PostCreateRequest;
import com.codecrafter.typhoon.domain.request.post.PostUpdateRequest;
import com.codecrafter.typhoon.domain.response.post.PostDetailResponse;
import com.codecrafter.typhoon.domain.response.post.SimplePostResponse;
import com.codecrafter.typhoon.service.FileService;
import com.codecrafter.typhoon.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/post")
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	private final FileService fileService;

	@Operation(summary = "상품 상세조회",
		description = "★단건 상품정보 상세조회</br>"
			+ "PostId = 숫자</br>"
			+ "{host}/api/post/1")
	@GetMapping("/{postId}")
	public ResponseEntity<PostDetailResponse> getPostDetail(@PathVariable Long postId) {
		PostDetailResponse postDetail = postService.getPostDetail(postId);
		return ResponseEntity.ok().body(postDetail);
	}

	@Operation(summary = "상품 목록조회(페이징)",
		description = "★상품 전체 목록조회</br>"
			+ "page = 숫자 / size = 숫자 / sort = 정렬할 필드명(id, craterAt, ...)</br>"
			+ "{host}/api/post/list?page=1")
	@GetMapping("/list")
	public ResponseEntity<?> getPostList(@PageableDefault(size = 10, sort = "id", direction = DESC) Pageable pageable) {
		Slice<SimplePostResponse> postList = postService.getPostList(pageable);
		return ResponseEntity.ok().body(postList);
	}

	@Operation(summary = "상품 등록",
		description = "★상품 신규 등록</br>"
			+ "title=문자 / content = 문자 / imagetPath = 문자 / isThumbnail = 썸네일여부(논리) / hashTagList = 문자배열</br>"
			+ "{</br>"
			+ "  \"title\": \"금장코트\",</br>"
			+ "  \"content\": \"단종된 코트 급처로 팝니다\",</br>"
			+ "  \"postImageRequestList\": [</br>"
			+ "    {</br>"
			+ "      \"imagePath\": \"\",</br>"
			+ "      \"isThumbnail\": true</br>"
			+ "    }</br>"
			+ "  ],</br>"
			+ "  \"hashTagList\": [</br>"
			+ "    \"롱코트\", \"공유\", \"단종제품\"</br>"
			+ "  ]</br>"
			+ "}")
	@PostMapping("/")
	public ResponseEntity<URI> createPost(@RequestBody PostCreateRequest postCreateRequest, @CurrentMember Member me) {
		Long postId = postService.createPost(postCreateRequest, me);
		URI uri = fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(postId)
			.toUri();
		return ResponseEntity.created(uri).body(uri);
	}

	@PatchMapping("/{postId}")
	public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
		Long id = postService.updatePost(postId, postUpdateRequest);
		return ResponseEntity.ok().build();
	}

	@CheckOwner
	@PostMapping("/{postId}/hashtags")
	public ResponseEntity<?> addHashtag(@PathVariable Long postId, @RequestBody HashtagsRequest hashtagsRequest) {
		postService.addHashtagsToPost(postId, hashtagsRequest);

		return ResponseEntity.ok().body("add hashtags success");
	}

	@CheckOwner
	@PostMapping("/{postId}/images")
	public ResponseEntity<?> addImage(@PathVariable Long postId, @RequestBody ImageRequest imageRequest) {
		postService.addImagesToPost(postId, imageRequest);
		return ResponseEntity.ok().body("addImages success");
	}

	@CheckOwner
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long postId) {
		postService.deletePost(postId);
		return ResponseEntity.ok().body("sucess");
	}

}
