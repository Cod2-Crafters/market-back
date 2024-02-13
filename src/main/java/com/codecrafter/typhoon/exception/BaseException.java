package com.codecrafter.typhoon.exception;

import lombok.Getter;

/**
 * 예외 추상클래스
 */
@Getter
public abstract class BaseException extends RuntimeException {

	private final int code;

	public BaseException(String message, int code) {
		super(message);
		this.code = code;
	}

	public BaseException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}

}
