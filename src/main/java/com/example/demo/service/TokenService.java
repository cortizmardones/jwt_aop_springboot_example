package com.example.demo.service;

import com.example.demo.dto.TokenResponseDTO;

public interface TokenService {
	
	TokenResponseDTO getToken(String subject);
	boolean validToken(String token);

}
