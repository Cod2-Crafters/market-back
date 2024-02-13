package com.codecrafter.typhoon.exception;

/**
 * RefreshToken이 없을때
 */
public class NoRefreshTokenException extends BaseException{
	private static final int STATUS = 400;

	private static final String DEFAULT_MSG = "NO TOKEN!!!!!!!";

	public NoRefreshTokenException(){
		super(DEFAULT_MSG, STATUS);
	}

	public NoRefreshTokenException(String message) {
		super(message, STATUS);
	}

	public NoRefreshTokenException(String message, Throwable cause) {
		super(message, cause, STATUS);
	}
}
