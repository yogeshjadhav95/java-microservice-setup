package com.prime.auth.security.jwt;

import org.springframework.security.core.AuthenticationException;

import com.prime.common.library.JwtToken;

public class JwtExpiredTokenException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 851565194166461363L;

	private JwtToken token;

	public JwtExpiredTokenException(String msg) {
		super(msg);
	}

	public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
		super(msg, t);
		this.token = token;
	}

	public String token() {
		return this.token.getToken();
	}
}
