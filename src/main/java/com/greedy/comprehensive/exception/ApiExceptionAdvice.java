package com.greedy.comprehensive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.greedy.comprehensive.exception.dto.ApiExceptionDto;

@RestControllerAdvice
public class ApiExceptionAdvice {
	
	@ExceptionHandler({UserNotFoundException.class, DuplicatedUserEmailException.class, LoginFailedException.class, IllegalArgumentException.class})
	public ResponseEntity<ApiExceptionDto> exceptionHandler(Exception e){
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ApiExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage()));
		
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiExceptionDto> exceptionHandler(RuntimeException e){
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
		
	}

}
