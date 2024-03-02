package com.codecrafter.typhoon.controller;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 테스트용
 */
@RequestMapping("/")
@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

	@GetMapping("/")
	public String Hello(@RequestParam(required = false) String err) {
		if (err == null) {
			return """
				***********
				HELLO WORLD
				***********
				""";
		}
		throw new RuntimeException();

	}

	/**
	 * 로그인 테스트
	 */
	@GetMapping("/logintest")
	public String authTest() {
		return "이 문구는, 로그인한 유저만 볼 수 있음!!";
	}

	@GetMapping("/log")
	public String getTodayLog() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String format = now.format(dateTimeFormatter);

		String path = System.getProperty("user.home") + "/log/" + format + "/info.log";
		return readLastNLines(new File(path), 200);
	}

	public String readLastNLines(File file, int nLines) {
		LinkedList<String> result = new LinkedList<>();
		int readLines = 0;
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");) {
			long fileLength = file.length() - 1;
			randomAccessFile.seek(fileLength);

			for (long pointer = fileLength; pointer >= 0; pointer--) {
				randomAccessFile.seek(pointer);
				char c;
				c = (char)randomAccessFile.read();
				if (c == '\n') {
					readLines++;
					if (readLines == nLines)
						break;
				}
			}

			// Now reading the required lines
			String line;
			while ((line = randomAccessFile.readLine()) != null) {
				result.add(new String(line.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			}
		} catch (Exception e) {
			throw new RuntimeException("로그파일읽다가 에러남" + e.getMessage());
		}

		return String.join("<br>", result);
	}

}
