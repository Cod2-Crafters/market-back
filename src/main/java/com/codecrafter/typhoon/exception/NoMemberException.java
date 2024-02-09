package com.codecrafter.typhoon.exception;

/**
 * Member를 찾을 수 없을때
 */
public class NoMemberException extends BaseException{

	private static final int STATUS = 404;
	private static final String DEFAULT_MSG = "NO Member Exists !!!!!!!";

	public NoMemberException(){
		super(DEFAULT_MSG, STATUS);
	}

	public NoMemberException(String message) {
		super(message, STATUS);
	}

	public NoMemberException(String message, Throwable cause) {
		super(message, cause, STATUS);
	}
}
