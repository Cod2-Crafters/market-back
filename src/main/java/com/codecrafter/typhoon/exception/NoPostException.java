package com.codecrafter.typhoon.exception;

/**
 * Post를 찾을 수 없을때
 */
public class NoPostException extends BaseException {

	private static final int STATUS = 404;
	private static final String DEFAULT_MSG = "NO Post Exists !!!!!!!";

	public NoPostException() {
		super(DEFAULT_MSG, STATUS);
	}

	public NoPostException(String message) {
		super(message, STATUS);
	}

	public NoPostException(String message, Throwable cause) {
		super(message, cause, STATUS);
	}
}
