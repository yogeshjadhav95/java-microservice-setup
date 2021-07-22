package com.prime.auth.security.jwt;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import com.prime.common.util.StringUtils;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

	private static final String HEADER_PREFIX = "Bearer";
	private static final String HEADER_BLANK = "Authorization header cannot be blank!";
	private static final String HEADER_SIZE = "Invalid authorization header size.";
	private final int length = HEADER_PREFIX.length();

	@Override
	public String extract(String header) {
		if (StringUtils.checkString(header).length() == 0) {
			throw new AuthenticationServiceException(HEADER_BLANK);
		}

		if (header.length() < length) {
			throw new AuthenticationServiceException(HEADER_SIZE);
		}
		return header.substring(length);
	}

}
