package com.prime.common.library;

public class JwtSettings {

	private JwtSettings() {

	}

	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";

	public static final Integer TOKEN_EXPIRATION_TIME = 120; // Number of minutes

	public static final String TOKEN_ISSUER = "user";

	public static final String TOKEN_SIGNING_KEY = "27wZpG-85bWjx-YM3Yn9-UlGtli-itqaf7-qPR0S2-aqzO5M-zajMDB-giLcMV-l2ieJs";

	public static final Integer REFRESH_TOKENEXP_TIME = 720; // Minutes

}
