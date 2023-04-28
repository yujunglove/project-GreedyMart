package com.greedy.comprehensive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.greedy.comprehensive.exception.dto.ApiExceptionDto;

@RestControllerAdvice
public class ApiExceptionAdvice {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiExceptionDto> exceptionHandler(RuntimeException e){
		
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ApiExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
		
	}

}
