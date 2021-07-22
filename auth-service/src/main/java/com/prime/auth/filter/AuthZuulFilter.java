package com.prime.auth.filter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.prime.common.library.JwtSettings;

@Component
public class AuthZuulFilter extends ZuulFilter {

	private static final String filterType = "pre";

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.addZuulRequestHeader(JwtSettings.JWT_TOKEN_HEADER_PARAM,
				ctx.getRequest().getHeader(JwtSettings.JWT_TOKEN_HEADER_PARAM));

		return null;
	}

	@Override
	public String filterType() {
		return filterType;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
