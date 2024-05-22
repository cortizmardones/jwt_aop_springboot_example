package com.example.demo.exception;

public class InvalidTokenException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidTokenException(String message) {
        super(message);
    }
}