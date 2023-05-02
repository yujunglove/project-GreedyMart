package com.greedy.comprehensive.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {

    private String grantType;
    private String memberName;
    private String accessToken;

    //언제까지 유효한지
    private Long accessTokenExpiresIn;
}
