package com.codecrafter.typhoon.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codecrafter.typhoon.domain.response.ErrorResponse;
import com.codecrafter.typhoon.domain.response.ValidationErrorResponse;
import com.codecrafter.typhoon.exception.BaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

	/**
	 * 바인딩 오류 처리
	 *
	 * @param e 바인딩오류
	 * @return ResponseEntity
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> invalidRequestHandler(MethodArgumentNotValidException e) {

		Map<String, String> validation = new HashMap<>();
		for (FieldError fieldError : e.getFieldErrors()) {
			validation.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ValidationErrorResponse body = ValidationErrorResponse.builder()
			// .status(400)
			.message("값 검증 실패")
			.validation(validation)
			.build();

		return ResponseEntity.status(400).body(body);
	}

	/**
	 * @param e 공통오류
	 * @return ResponseEntity
	 */
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> controlException(BaseException e) {
		ErrorResponse body = ErrorResponse.builder()
			.message(e.getMessage())
			.code(e.getCode())
			.build();

		return ResponseEntity.status(e.getCode()).body(body);
	}

	/**
	 * 예상치 못한 오류 처리
	 *
	 * @param e 예상치 못한 오류
	 * @return ResponseEntity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> except(Exception e) {
		return ResponseEntity.status(200).
			body(e.getClass());
	}
}
