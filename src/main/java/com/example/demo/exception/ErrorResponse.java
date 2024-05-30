package com.example.demo.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
		
		String errorName,
		int errorCode,
		String errorMessage,
	    String timestamp
	    
		) {

}
