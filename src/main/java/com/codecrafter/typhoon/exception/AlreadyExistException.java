package com.codecrafter.typhoon.exception;

public class AlreadyExistException extends BaseException {

	private static final int STATUS = 409;

	private static final String DEFAULT_MSG = "!!!!중복!!!!";

	public AlreadyExistException() {
		super(DEFAULT_MSG, STATUS);
	}

	public AlreadyExistException(String message) {
		super(message, STATUS);
	}

	public AlreadyExistException(String message, Throwable cause) {
		super(message, cause, STATUS);
	}
}
