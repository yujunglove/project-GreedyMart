package com.greedy.comprehensive.member.service;

import com.greedy.comprehensive.member.dto.MemberDto;
import com.greedy.comprehensive.member.entity.Member;
import com.greedy.comprehensive.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    public CustomUserDetailService(ModelMapper modelMapper,MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
            this.modelMapper = modelMapper;
    }

    //아이디 값을 받아서 아이디값을 일치하는 값을 시큐리티어댄티 케이션으로 만들고 싶다.

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {


        return memberRepository.findByMemberId(userId)
                .map(user -> addAuthorities(user))
                .orElseThrow(() -> new RuntimeException(userId + "를 찾을 수 없습니다."));
    }

    //Member entity를 MemberDto로 가공하면서 authorities의 값도 가공해서 반환하는 메소드
    private MemberDto addAuthorities(Member member) {
        MemberDto memberDto = modelMapper.map(member, MemberDto.class);
        memberDto.setAuthorities((Arrays.asList(new SimpleGrantedAuthority(memberDto.getMemberRole()))));

        return memberDto;

    }
}
