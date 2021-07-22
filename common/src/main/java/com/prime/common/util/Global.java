package com.prime.common.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class Global {


	
	
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String KEY_LOCATION = "location";
	public static final Locale LOCALE = Locale.getDefault();

	private Global() {
	}

	public static final String KEY_CURRANCY = "curr";
	public static final String KEY_DEVICE_ID = "deviceId";
	public static final String UUID_PATTERN = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
	public static final String ALPHA_NUMERIC_PATTERN = "^[a-zA-Z0-9]+$";
	public static final String ID_EMPTY = "Please provide Valid ID";
	public static final String EMPTY = "";
	public static final String TOKEN = "token";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final String RESET_PASS = "reset";
	public static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";
	public static final String X_FORWARDED_PROTO = "X-FORWARDED-PROTO";
	public static final String ID_GENERATION_STRATEGY = "uuid2";
	public static final String ID_GENERATION_NAME = "uuid";
	public static final String ID_GENERATOR = "uuid";
	public static final String ID_ASSIGNED_GENERATION_STRATEGY = "assigned";
	public static final String ID_ASSIGNED_GENERATION_NAME = "idGen";
	public static final String USER_AGENT = "LoginUserDetails-Agent";
	public static final String USER_NOT_FOUND = "LoginUserDetails not found";
	public static final String LOGIN_DETAILS = "LOGIN_DETAILS";
	public static final String KEY_LAST_LOGIN = "lastLogin";
	public static final String KEY_LAST_FAILD = "lastFailedLogin";
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_TENANT_TYPE = "tenantType";
	public static final String KEY_TENANT_ID = "tenantId";
	public static final String KEY_TIME_ZONE = "timeZone";
	public static final String KEY_DECIMAL_SCALE = "scale";
	public static final String KEY_SCOPES = "scopes";
	public static final String NOT_FOUND = "No record found";
	public static final String NOT_VALID_TOKEN = "Invalid security token";
	public static final String COMMON_ERROR = "Something went wrong, please contact to support team";
	public static final String BAD_CREDENTIALS = "Invalid Authentication provided";
	public static final String KEY_LAST_PASSWORD_CHANGE = "lastPasswordChanged";
	public static final Integer DEFAULT_DECIMAL_SCALE = 4;
	public static final String DEFAULT_TIME_ZONE = "GMT+0:00";
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm a";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_X_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_FORWARDED");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("HTTP_VIA");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
