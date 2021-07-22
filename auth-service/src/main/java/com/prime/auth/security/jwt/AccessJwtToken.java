package com.prime.auth.security.jwt;

import com.prime.common.library.JwtToken;

public class AccessJwtToken implements JwtToken {

	private final String rawToken;

	AccessJwtToken(final String token) {
		this.rawToken = token;
	}

	public String getToken() {
		return this.rawToken;
	}

}
