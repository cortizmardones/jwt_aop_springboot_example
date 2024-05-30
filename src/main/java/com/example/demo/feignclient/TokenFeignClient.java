package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.dto.ValidTokenResponse;

import feign.FeignException;


@FeignClient(name = "TokenFeignClient", url = "http://localhost:8086/demo/v1/token")
public interface TokenFeignClient {
	
	
	// Metodo con patrón RETRY (5 reintentos cada 5 segundos)
	
    @GetMapping("/validToken")
    @Retryable(retryFor = { FeignException.class }, maxAttempts = 5, backoff = @Backoff(delay = 5000))
    ValidTokenResponse validToken(@RequestHeader("Authorization") String token);
    
}
