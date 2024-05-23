package com.example.demo.service;

import com.example.demo.dto.TokenResponseDTO;
import com.example.demo.dto.ValidTokenResponse;

public interface TokenService {
	
	TokenResponseDTO getToken(String subject);
	ValidTokenResponse validToken(String token);

}
