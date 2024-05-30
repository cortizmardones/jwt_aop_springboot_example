package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.dto.ValidTokenResponse;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "TokenFeignClient3", url = "http://localhost:8086/demo/v1/tokenS")
public interface TokenFeignClient3 {
	
	
    @GetMapping("/validToken")
    @CircuitBreaker(name = "tokenService", fallbackMethod = "fallback")
    ValidTokenResponse validToken(@RequestHeader("Authorization") String token);
    
    //Cuando el Circuit Breaker se abre lanza la excepción CallNotPermittedException , es importante capturar esa excepción en el metodo fallback.
    default ValidTokenResponse fallback(CallNotPermittedException e) {
        return new ValidTokenResponse("JWT", false, e.getMessage());
    }
    

}
