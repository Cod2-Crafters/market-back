package com.codecrafter.typhoon.controller;

import static java.lang.Math.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codecrafter.typhoon.service.RedisService;

import io.swagger.v3.oas.annotations.Operation;
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

	@Operation(summary = "JH님이 요청한 로그조회기능",
		description = """
			로그 파일 끝에서 최대 개행 200개읽고 개행 br로 바꿔서 리턴
			""")
	@GetMapping("/log")
	public String getTodayLog() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String format = now.format(dateTimeFormatter);

		String path = System.getProperty("user.home") + "/log/" + format + "/info.log";
		return readLastNLines(new File(path), 200);
	}

	public String readLastNLines(File file, int nLines) {
		LinkedList<String> res = new LinkedList<>();
		int readLines = 0;
		try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
			FileChannel channel = raf.getChannel();

			long fileSize = channel.size();
			long bufferSize = min(fileSize, 8192);
			long position = fileSize - bufferSize;
			MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, position, bufferSize);

			StringBuilder line = new StringBuilder();
			for (long i = bufferSize - 1; i >= 0; i--) {
				byte b = buffer.get((int)i);
				char c = (char)b;
				if (c == '\n') {
					if (line.length() > 0) {
						res.addFirst(line.reverse().toString());
						line.setLength(0);
						if (res.size() == nLines)
							break;
					}
				} else {
					line.append(c);
				}
			}
			if (!line.isEmpty()) { // 버퍼에 남아있을때
				res.addFirst(line.reverse().toString());
			}

			String result = String.join("<br>", res);
			return result;
		} catch (Exception e) {
			throw new RuntimeException("로그파일읽다가 에러남" + e.getMessage());
		}

	}

	private final RedisService redisService;

	@RequestMapping("/mytest")
	public void test() {
		redisService.persistAllDailyPostViewCountToDB();
	}

}
