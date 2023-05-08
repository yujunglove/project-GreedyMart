package com.greedy.comprehensive.member.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.greedy.comprehensive.exception.UserNotFoundException;
import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.member.entity.Member;
import com.greedy.comprehensive.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final ModelMapper modelMapper;
	
	public MemberService(MemberRepository memberRepository, ModelMapper modelMapper) {
		this.memberRepository = memberRepository;
		this.modelMapper = modelMapper;
	}

	public MemberDto selectMyInfo(Long memberCode) {
		
		log.info("[MemberService] selectMyInfo start ============================= ");
		log.info("[MemberService] memberCode : {}", memberCode);
		
		Member member = memberRepository.findById(memberCode)
				.orElseThrow(() -> new UserNotFoundException(memberCode + "를 찾을 수 없습니다."));
				
		log.info("[MemberService] selectMyInfo end ============================= ");
		return modelMapper.map(member, MemberDto.class);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
