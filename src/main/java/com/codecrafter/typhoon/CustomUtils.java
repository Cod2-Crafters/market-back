package com.codecrafter.typhoon;

import org.springframework.util.StringUtils;

public class CustomUtils {

	public static String getChosungString(String str) {
		if (!StringUtils.hasText(str))
			return null;
		char[] charArray = str.toCharArray();
		StringBuilder res = new StringBuilder();
		for (char c : charArray) {
			res.append(getChosung(c));
		}
		return res.toString();

	}

	public static char getChosung(char c) {
		if (!(c >= '가' && c <= '힣')) {
			return c; // 음절이 아니라면
		}

		// 한글 unicode =  [{(초성 인덱스)×588}+{(중성 인덱스)×28}+(종성 인덱스)] + 44032
		final char[] CHOSUNG = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
			'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

		//젤첫문자와의 거리를 구하고, 모든 경우의 수로(중성*종성) 인덱스를 구함
		int idx = (c - '가') / (21 * 28);
		return CHOSUNG[idx];

	}

	private CustomUtils() {
		throw new AssertionError("생성될일 없겠지");
	}
}
