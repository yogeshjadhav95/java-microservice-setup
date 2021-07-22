package com.prime.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {

	public TokenResponse(String token, String refreshToken) {
		this.token = token;
		this.refreshToken = refreshToken;
	}

	private String token;
	private String refreshToken;

}
