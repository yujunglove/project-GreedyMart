package com.greedy.comprehensive.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
	
	private String grantType;
	private String memberName;
	private String accessToken;
	private Long accessTokenExpiresIn;
	
}
