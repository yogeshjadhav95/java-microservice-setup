package com.prime.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prime.auth.config.SecurityConfig;
import com.prime.auth.security.jwt.AuthenticatedUser;
import com.prime.auth.security.jwt.TokenExtractor;
import com.prime.common.library.JwtSettings;
import com.prime.common.library.LoginUserDetails;

import io.jsonwebtoken.Jwts;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	TokenExtractor tokenExtractor;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String header = request.getHeader(JwtSettings.JWT_TOKEN_HEADER_PARAM);

		if (SecurityContextHolder.getContext().getAuthentication() == null && header != null) {
			LoginUserDetails userContext = AuthenticatedUser.get(Jwts.parser()
					.setSigningKey(JwtSettings.TOKEN_SIGNING_KEY).parseClaimsJws(tokenExtractor.extract(header)));
			SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken(userContext, null, SecurityConfig.grantedAuthorities));
		}

		chain.doFilter(request, response);
	}

}
