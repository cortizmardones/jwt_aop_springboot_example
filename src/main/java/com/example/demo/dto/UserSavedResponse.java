package com.example.demo.dto;

import lombok.Builder;

@Builder
public record UserSavedResponse(
		
		String message,
		String dateToOperation
		
		) {

}
