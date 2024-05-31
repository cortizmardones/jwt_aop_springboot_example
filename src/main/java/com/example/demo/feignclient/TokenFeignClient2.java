package com.example.demo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.dto.ValidTokenResponse;

import feign.FeignException;


@FeignClient(name = "TokenFeignClient2", url = "http://localhost:8086/demo/v1/tokenS")
public interface TokenFeignClient2 {
	
	// Metodo con patrón CIRCUIT_BREAKER
	
	// "include" Tipos de excepciones que, al ser lanzadas, deberían ser contadas como 'fallas' por el circuit breaker.
	// "maxAttempts" N° de fallas para ABRIR el circuito.
	// "openTimeout" Tiempo de vida en milisegundos que el circuito permanece ABIERTO no permitiendo llamadas al servicio remoto.
	// "resetTimeout" Tiempo para reintentar cerrar el circuito.
	
    @GetMapping("/validToken")
    @CircuitBreaker(value = {Exception.class}, maxAttempts = 5, openTimeout = 20000, resetTimeout = 15000)
    ValidTokenResponse validToken(@RequestHeader("Authorization") String token);
    

    
    

}
