package com.greedy.comprehensive.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.comprehensive.common.ResponseDto;
import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.member.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	/* 1. 회원 가입 */
	@PostMapping("/signup")
	public ResponseEntity<ResponseDto> signup(@RequestBody MemberDto memberDto) {
		
		authService.signup(memberDto);
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "회원 가입 완료"));
	}
	
	/* 2. 로그인 */
	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(@RequestBody MemberDto memberDto) {
		
		return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "로그인 완료", authService.login(memberDto)));
	}
	
	
	
	
	
}








