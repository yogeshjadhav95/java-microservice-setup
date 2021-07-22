package com.prime.auth.security.jwt;

import java.util.Calendar;
import java.util.Collections;

import org.springframework.stereotype.Component;

import com.jsoniter.output.JsonStream;
import com.prime.common.library.JwtSettings;
import com.prime.common.library.JwtToken;
import com.prime.common.library.LoginUserDetails;
import com.prime.common.util.EncryptionUtils;
import com.prime.common.util.Global;
import com.prime.common.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenFactory {

	private static final String JWT_EXCEPTION_MSG = "Cannot create JWT Token without username";

	public AccessJwtToken createAccessJwtToken(LoginUserDetails userContext) {
		if (StringUtils.checkString(userContext.getLoginId()).length() == 0) {
			throw new IllegalArgumentException(JWT_EXCEPTION_MSG);
		}

		Claims claims = Jwts.claims().setSubject(userContext.getLoginId());
		claims.put(Global.KEY_ID, EncryptionUtils.encrypt(JsonStream.serialize(userContext)));
		claims.put(Global.KEY_TENANT_TYPE, userContext.getTenantType());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, JwtSettings.TOKEN_EXPIRATION_TIME);

		String token = Jwts.builder().setClaims(claims).setIssuer(JwtSettings.TOKEN_ISSUER)
				.setIssuedAt(Calendar.getInstance().getTime()).setExpiration(cal.getTime())
				.signWith(SignatureAlgorithm.HS512, JwtSettings.TOKEN_SIGNING_KEY).compact();

		return new AccessJwtToken(token);
	}

	public JwtToken createRefreshToken(LoginUserDetails userContext) {
		if (StringUtils.checkString(userContext.getLoginId()).length() == 0) {
			throw new IllegalArgumentException(JWT_EXCEPTION_MSG);
		}

		Claims claims = Jwts.claims().setSubject(userContext.getLoginId());
		claims.put(Global.KEY_ID, EncryptionUtils.encrypt(JsonStream.serialize(userContext)));
		claims.put(Global.KEY_SCOPES, Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));
		claims.put(Global.KEY_TENANT_TYPE, userContext.getTenantType());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, JwtSettings.REFRESH_TOKENEXP_TIME);

		String token = Jwts.builder().setClaims(claims).setIssuer(JwtSettings.TOKEN_ISSUER)
				.setIssuedAt(Calendar.getInstance().getTime()).setExpiration(cal.getTime())
				.signWith(SignatureAlgorithm.HS512, JwtSettings.TOKEN_SIGNING_KEY).compact();

		return new AccessJwtToken(token);
	}
}
