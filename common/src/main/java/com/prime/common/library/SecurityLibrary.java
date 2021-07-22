package com.prime.common.library;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jsoniter.JsonIterator;
import com.prime.common.exception.ApplicationException;
import com.prime.common.util.EncryptionUtils;
import com.prime.common.util.Global;
import com.prime.common.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class SecurityLibrary {

	private SecurityLibrary() {

	}

	public static LoginUserDetails getLoggedInUser() throws Exception {
		return getAuthenticatedUser();
	}

	public static String getTenantId() throws Exception {
		return getAuthenticatedUser().getTenantId();
	}

	public static String getId() throws Exception {
		return getAuthenticatedUser().getId();
	}

	public static String getTenantType() throws Exception {
		return getAuthenticatedUser().getTenantType();
	}

	private static LoginUserDetails getAuthenticatedUser() throws Exception {

		Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(JwtSettings.TOKEN_SIGNING_KEY)
				.parseClaimsJws(extract(getCurrentHttpRequest().getHeader(JwtSettings.JWT_TOKEN_HEADER_PARAM)));

		String json = EncryptionUtils.decrypt(jwsClaims.getBody().get(Global.KEY_ID, String.class));
		return JsonIterator.deserialize(json, LoginUserDetails.class);

	}

	public static String getTimeZone() throws Exception {
		return getAuthenticatedUser().getTimeZone();
	}

	public static String getDeviceId() throws Exception {
		return getAuthenticatedUser().getDeviceId();
	}

	public static String getCurrency() throws Exception {
		return getAuthenticatedUser().getCurrency();
	}

	public static String getLangCode() {
		try {
			String lang = getAuthenticatedUser().getLangCode();
			return StringUtils.isNotEmpty(lang) ? lang : Locale.ENGLISH.getLanguage();
		} catch (Exception e) {
			return Locale.ENGLISH.getLanguage();
		}
	}

	public static String[] getLocation() throws Exception {

		return new String[] { StringUtils.checkString(getAuthenticatedUser().getLatitude()),
				StringUtils.checkString(getAuthenticatedUser().getLongitude()) };
	}

	public static LoginUserDetails getForAuditAware() {
		return null;
	}

	public static int getDecimalScale() {

		return Global.DEFAULT_DECIMAL_SCALE;
	}

	public static HttpServletRequest getCurrentHttpRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			return request;
		}
		return null;
	}

	private static final String HEADER_PREFIX = "Bearer";
	private static final String HEADER_BLANK = "Authorization header cannot be blank!";
	private static final String HEADER_SIZE = "Invalid authorization header size.";
	private static final int length = HEADER_PREFIX.length();

	public static String extract(String header) throws ApplicationException {
		if (StringUtils.checkString(header).length() == 0) {
			throw new ApplicationException(HEADER_BLANK);
		}

		if (header.length() < length) {
			throw new ApplicationException(HEADER_SIZE);
		}
		return header.substring(length);
	}
}
