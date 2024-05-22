package com.example.demo.dto;

import lombok.Builder;

@Builder
public record User(
		
		String rut,
		String nombre,
		String apellido,
		String edad
		
		) {

}
