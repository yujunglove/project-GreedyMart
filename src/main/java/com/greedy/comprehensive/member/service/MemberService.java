package com.greedy.comprehensive.member.service;

import com.greedy.comprehensive.exception.UserNotFoundException;
import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.member.entity.Member;
import com.greedy.comprehensive.member.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    public MemberService(MemberRepository memberRepository, ModelMapper modelMapper) {
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    public MemberDto selectMyInfo(Long memberCode) {
        Member member = memberRepository.findById(memberCode)
                .orElseThrow(() -> new UserNotFoundException(memberCode + "를 찾을 수 없습니다."));

        return modelMapper.map(member,MemberDto.class);
    }
}
