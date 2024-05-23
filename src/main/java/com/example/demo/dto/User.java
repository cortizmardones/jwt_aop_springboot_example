package com.example.demo.dto;

import lombok.Builder;

@Builder
public record User(
		
		String rut,
		String name,
		String lastName,
		String age
		
		) {

}
