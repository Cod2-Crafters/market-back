package com.codecrafter.typhoon.controller;

import java.net.URI;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codecrafter.typhoon.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/file")
@Slf4j
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@Operation(summary = "파일 업로드",
			description = 	"""
      						★파일 단/다건 업로드</br>
      						return success
							""")
	@PostMapping("/upload")
	public ResponseEntity<String> saveFile(@RequestParam MultipartFile file) throws Exception {
		URI uri = fileService.storeFile(file);
		log.info("uri={}", uri);
		return ResponseEntity.created(uri).body("success");
	}

	// @GetMapping("/{filename:.+}")
	// public ResponseEntity<?> serveFile(@PathVariable String filename) throws MalformedURLException {
	// 	Resource resource = fileService.loadResource(filename);
	// 	return ResponseEntity.ok()
	// 		.body(resource);
	// }
}
