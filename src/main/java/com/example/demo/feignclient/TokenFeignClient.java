package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.dto.ValidTokenResponse;

@FeignClient(name = "TokenFeignClient", url = "http://localhost:8086/demo/v1/token")
public interface TokenFeignClient {
	
    @GetMapping("/validToken")
    @Retryable(retryFor = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    ValidTokenResponse validToken(@RequestHeader("Authorization") String token);

}
