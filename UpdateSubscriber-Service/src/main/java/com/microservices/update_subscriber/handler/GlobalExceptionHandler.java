package com.microservices.update_subscriber.handler;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.microservices.update_subscriber.exception.ErrorIDGenerator;
import com.microservices.update_subscriber.exception.ExceptionResponse;
import com.microservices.update_subscriber.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//		Map<String, String> errors = new HashMap<>();
//
//		exception.getBindingResult().getFieldErrors()
//				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		ExceptionResponse response = new ExceptionResponse();
		response.setErrorid(ErrorIDGenerator.getErrorId());
		response.setErrorcode(422);
		response.setMessage(Optional.ofNullable(exception.getBindingResult().getFieldError())
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("Validation Exception Occured,Please Enter Valid Data"));
		response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		response.setTimestamp(LocalTime.now().toString());
		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ExceptionResponse> handleValidationException(ValidationException exception) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorid(ErrorIDGenerator.getErrorId());
		response.setErrorcode(exception.getErrorcode());
		response.setMessage(exception.getMessage());
		response.setStatus(exception.getStatus());
		response.setTimestamp(LocalTime.now().toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	
}
