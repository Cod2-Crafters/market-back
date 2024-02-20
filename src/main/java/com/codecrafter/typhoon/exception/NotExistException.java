package com.codecrafter.typhoon.exception;

/**
 * 해당 요소가 없을때 공통
 */
public class NotExistException extends BaseException {

	private static final int STATUS = 404;

	private static final String DEFAULT_MSG = "존재하지 않음!!";

	public NotExistException() {
		super(DEFAULT_MSG, STATUS);
	}

	public NotExistException(String message) {
		super(message, STATUS);
	}

	public NotExistException(String message, Throwable cause) {
		super(message, cause, STATUS);
	}
}
