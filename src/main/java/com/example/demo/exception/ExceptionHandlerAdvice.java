package com.example.demo.exception;

import static com.example.demo.utils.Constant.CHILE_TIME_ZONE;
import static com.example.demo.utils.Constant.TIME_FORMATTER;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import feign.FeignException.FeignClientException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName(ex.getClass().getSimpleName())
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.timestamp(getDateTime())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FeignClientException.class)
	public ResponseEntity<ErrorResponse> handleFeignClientException(FeignClientException ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName(ex.getClass().getSimpleName())
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.timestamp(getDateTime())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// Excepcion que se genera cuando apuntas a un servidor que esta activo pero no reconoce la ruta del endpoint que envias (La pongo mal a proposito)
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName(ex.getClass().getSimpleName())
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.timestamp(getDateTime())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// Excepcion que se genera cuando fallan todos los reintentos (retry)
	@ExceptionHandler(ExhaustedRetryException.class)
	public ResponseEntity<ErrorResponse> handleExhaustedRetryException(ExhaustedRetryException ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName(ex.getClass().getSimpleName())
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.timestamp(getDateTime())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// Excepcion que se genera cuando el circuit breaker se abre y NO PERMITE mas llamadas.
	@ExceptionHandler(CallNotPermittedException.class)
	public ResponseEntity<ErrorResponse> handleExhaustedRetryException(CallNotPermittedException ex) {

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName(ex.getClass().getSimpleName())
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage())
				.timestamp(getDateTime())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// Excepcion personalizada.
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
		
		String nameExcepcion = ex.getMessage().contains("CircuitBreaker") ? "CallNotPermittedException" : ex.getClass().getSimpleName();

		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorName(nameExcepcion)
				.errorCode(HttpStatus.UNAUTHORIZED.value())
				.errorMessage(ex.getMessage())
				.timestamp(getDateTime())
				.build();

		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	
	public String getDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(CHILE_TIME_ZONE));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMATTER);
        return  zonedDateTime.format(formatter);
	}

}
