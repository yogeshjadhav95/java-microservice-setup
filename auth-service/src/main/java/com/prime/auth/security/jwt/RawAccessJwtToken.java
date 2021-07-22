package com.prime.auth.security.jwt;

import org.springframework.security.authentication.BadCredentialsException;

import com.prime.common.library.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class RawAccessJwtToken implements JwtToken {

	private final String token;
	private static final String TOKEN_INVALID = "Invalid JWT token: ";
	private static final String JWT_TOKEN_EXPIRED = "JWT Token expired";

	public RawAccessJwtToken(String token) {
		this.token = token;
	}

	public Jws<Claims> parseClaims(String signingKey) {
		try {
			return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
		} catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
			throw new BadCredentialsException(TOKEN_INVALID, ex);
		} catch (ExpiredJwtException expiredEx) {
			throw new JwtExpiredTokenException(this, JWT_TOKEN_EXPIRED, expiredEx);
		}
	}

	@Override
	public String getToken() {
		return token;
	}

}
