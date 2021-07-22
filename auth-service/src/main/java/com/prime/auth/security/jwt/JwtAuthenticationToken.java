package com.prime.auth.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3355061125055493649L;
	private RawAccessJwtToken rawAccessToken;
	private AuthenticatedUser userContext;

	private static final String ILLEGAL_MESSAGE = "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead";

	public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
		super(null);
		this.rawAccessToken = unsafeToken;
		this.setAuthenticated(false);
	}

	public JwtAuthenticationToken(AuthenticatedUser userContext) {
		super(userContext.getAuthorities());
		this.eraseCredentials();
		this.userContext = userContext;
		super.setAuthenticated(true);
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		if (authenticated) {
			throw new IllegalArgumentException(ILLEGAL_MESSAGE);
		}
		super.setAuthenticated(false);
	}

	@Override
	public Object getCredentials() {
		return rawAccessToken;
	}

	@Override
	public Object getPrincipal() {
		return this.userContext;
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.rawAccessToken = null;
	}
}
