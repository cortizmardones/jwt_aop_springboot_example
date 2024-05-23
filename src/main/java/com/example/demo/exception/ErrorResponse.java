package com.example.demo.exception;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ErrorResponse(
		
		String errorName,
		int errorCode,
		String errorMessage,
	    LocalDateTime timestamp
	    
		) {

}
