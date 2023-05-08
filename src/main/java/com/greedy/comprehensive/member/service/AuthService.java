package com.greedy.comprehensive.member.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greedy.comprehensive.exception.DuplicatedUserEmailException;
import com.greedy.comprehensive.exception.LoginFailedException;
import com.greedy.comprehensive.jwt.TokenProvider;
import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.member.dto.TokenDto;
import com.greedy.comprehensive.member.entity.Member;
import com.greedy.comprehensive.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;
	private final TokenProvider tokenProvider;
	
	public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, TokenProvider tokenProvider) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
		this.tokenProvider = tokenProvider;
	}
	
	/* 1. 회원 가입 */
	@Transactional
	public void signup(MemberDto memberDto) {
		log.info("[AuthService] signup start ======================================");
		log.info("[AuthService] memberDto : {}", memberDto);
		
		/* 이메일 중복 시 가입 불가 처리 */
		if(memberRepository.findByMemberEmail(memberDto.getMemberEmail()) != null) {
			log.info("[AuthService] 이메일이 중복 됩니다.");
			throw new DuplicatedUserEmailException("이메일이 중복 됩니다.");
		}
		
		/* 비밀번호 암호화 처리 */
		memberDto.setMemberPassword(passwordEncoder.encode(memberDto.getMemberPassword()));
		
		/* 영속성 컨텍스트에 저장 */
		memberRepository.save(modelMapper.map(memberDto, Member.class));
		
		log.info("[AuthService] signup end ======================================");
	}

	/* 2. 로그인 */
	public TokenDto login(MemberDto memberDto) {
		
		log.info("[AuthService] login start ======================================");
		log.info("[AuthService] memberDto : {}", memberDto);
		
		// 1. 아이디로 DB에서 해당 유저가 있는지 조회
		Member member = memberRepository.findByMemberId(memberDto.getMemberId())
				.orElseThrow(() -> new LoginFailedException("잘못 된 아이디 또는 비밀번호입니다."));
		
		// 2. 비밀번호 매칭 확인
		if(!passwordEncoder.matches(memberDto.getMemberPassword(), member.getMemberPassword())) {
			throw new LoginFailedException("잘못 된 아이디 또는 비밀번호입니다.");
		}
		
		// 3. 토큰 발급 
		TokenDto tokenDto = tokenProvider.generateTokenDto(modelMapper.map(member, MemberDto.class));
		log.info("[AuthService] tokenDto : {}", tokenDto);
		
		log.info("[AuthService] login end ======================================");
		return tokenDto;
	}
	
	
	
	
	
	
	
	

}
