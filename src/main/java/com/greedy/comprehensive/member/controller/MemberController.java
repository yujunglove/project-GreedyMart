package com.greedy.comprehensive.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greedy.comprehensive.common.ResponseDto;
import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.member.service.MemberService;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	/* 3. 내 정보 조회 */
	@GetMapping("/members")
	public ResponseEntity<ResponseDto> selectMyInfo(@AuthenticationPrincipal MemberDto member){
		
		return ResponseEntity
				.ok()
				.body(new ResponseDto(HttpStatus.OK, "조회 완료", memberService.selectMyInfo(member.getMemberCode())));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
