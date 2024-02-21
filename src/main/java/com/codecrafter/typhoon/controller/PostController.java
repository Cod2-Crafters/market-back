package com.codecrafter.typhoon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.repository.FileService;
import com.codecrafter.typhoon.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	PostService postService;

	FileService fileService;

	/**
	 * 이미지 한장씩 업로드 (MultipartFile)
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	// @PostMapping("/upload-image")
	// public ResponseEntity<String> saveFile(@RequestParam MultipartFile file) throws Exception {
	// 	String storeFileName = fileService.storeFile(file);
	// 	return null;
	// }

	// 상품 등록
	// @PostMapping
	// public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
	//     return ResponseEntity.status(HttpStatus.CREATED)
	//                          .body("createPost");
	// }

	// 상품 수정
	// @PutMapping("/{postId}")
	// public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest) {
	// 	return ResponseEntity.ok().body("updatePost");
	// }

	// 상품 삭제
	// @DeleteMapping("/{postId}")
	// public ResponseEntity<?> deletePost(@PathVariable Long postId) {
	// 	return ResponseEntity.ok().body("deletePost");
	// }
	//
	// // 임시 저장
	// @PostMapping("/draft")
	// public ResponseEntity<?> saveDraft(@RequestBody DraftRequest draftRequest) {
	// 	return ResponseEntity.status(HttpStatus.CREATED).body("draft");
	// }
	//
	// // 임시 저장불러오기
	// @GetMapping("/draft/{draftId}")
	// public ResponseEntity<?> getDraft(@PathVariable Long draftId) {
	// 	return ResponseEntity.ok().body("getDraft");
	// }
	//
	// // 상품 목록 조회
	// @GetMapping
	// public ResponseEntity<List<PostResponse>> getPosts(@RequestParam(required = false) Long categoryId) {
	// 	List postList = null;
	// 	return ResponseEntity.ok(postList);
	// }
	//
	// // 상품 상세 조회
	// @GetMapping("/{postId}")
	// public ResponseEntity<PostResponse> getPostDetail(@PathVariable Long postId) {
	// 	PostResponse postResponse = null;
	// 	return ResponseEntity.ok(postResponse);
	// }
	//
	// // 해시태그 등록
	// @PostMapping("/{postId}/hashtags")
	// public ResponseEntity<?> addHashtags(@PathVariable Long postId, @RequestBody List<String> hashtags) {
	// 	return ResponseEntity.status(HttpStatus.CREATED).body("addHashtags");
	// }
	//
	// // 해시태그 삭제
	// @DeleteMapping("/{postId}/hashtags")
	// public ResponseEntity<?> removeHashtags(@PathVariable Long postId, @RequestBody List<String> hashtags) {
	// 	return ResponseEntity.ok().body("removeHashtags");
	// }

}
