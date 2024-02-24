package com.codecrafter.typhoon.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {

	@Value("${file.base.dir}")
	private String baseDir;

	public Resource loadResource(String fileName) throws MalformedURLException {
		String fullPath = createFullPath(fileName);
		UrlResource resource = new UrlResource("file:" + fullPath);
		return resource; //TODO 취약점??
	}

	private URI createFileAccessUri(String storeFileName) {
		URI uri = ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.path("/api/file/static/")
			.path(storeFileName)
			.build()
			.toUri();
		return uri;
	}

	public URI storeFile(MultipartFile multipartFile) throws IOException {
		if (multipartFile.isEmpty()) {
			return null;
		}
		String originalFilename = multipartFile.getOriginalFilename();
		String ext = getExt(originalFilename);
		String storeFileName = createStoreFileName(ext);
		String fullPath = createFullPath(storeFileName);
		multipartFile.transferTo(new File(fullPath));
		log.info("file fullpath={}", fullPath);
		return createFileAccessUri(storeFileName);
	}

	private static String createStoreFileName(String ext) {
		String uuid = UUID.randomUUID().toString();
		return uuid + '.' + ext;
	}

	private static String getExt(String originalFilename) {
		int idx = originalFilename.lastIndexOf('.');
		return originalFilename.substring(idx + 1);
	}

	private String createFullPath(String fileName) {
		return baseDir + fileName;
	}

}
