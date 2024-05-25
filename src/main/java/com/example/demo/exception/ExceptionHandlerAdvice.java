package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feign.FeignException.FeignClientException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName("InvalidTokenException")
				.errorCode(HttpStatus.UNAUTHORIZED.value())
				.errorMessage(ex.getMessage())
				.timestamp(LocalDateTime.now())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(FeignClientException.class)
	public ResponseEntity<ErrorResponse> handleFeignClientException(FeignClientException ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName("FeignClientException")
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.timestamp(LocalDateTime.now())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
