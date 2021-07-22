package com.prime.auth.security.jwt;

import java.util.Optional;

import com.prime.common.library.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public class RefreshToken implements JwtToken {

	private final Jws<Claims> claims;

	private RefreshToken(Jws<Claims> claims) {
		this.claims = claims;
	}

	public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
		Jws<Claims> claims = token.parseClaims(signingKey);

		return Optional.of(new RefreshToken(claims));
	}

	@Override
	public String getToken() {
		return null;
	}

	public String getJti() {
		return claims.getBody().getId();
	}

	public String getSubject() {
		return claims.getBody().getSubject();
	}
}
