package com.prime.auth.security.jwt;

public interface TokenVerifier {
	boolean verify(String jti);
}
