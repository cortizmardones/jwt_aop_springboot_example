package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TokenResponseDTO;
import com.example.demo.dto.ValidTokenResponse;
import com.example.demo.service.TokenService;

@RestController
@RequestMapping("/v1/token")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;

	@GetMapping("/getToken")
	public TokenResponseDTO getToken(@RequestParam String subject) {
		return tokenService.getToken(subject);
	}
	
	@GetMapping("/validToken")
	public ValidTokenResponse validToken(@RequestParam String token) {
		return tokenService.validToken(token);
	}
	
}
