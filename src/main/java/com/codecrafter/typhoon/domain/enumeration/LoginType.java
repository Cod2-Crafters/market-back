package com.codecrafter.typhoon.domain.enumeration;

public enum LoginType {
	KAKAO("카카오"),
	NAVER("네이버"),
	GOOGLE("구글"),
	BASIC("일반");

	private String type;

	public String getType(){
		return type;
	}

	LoginType(String type) {
		this.type = type;
	}
}
