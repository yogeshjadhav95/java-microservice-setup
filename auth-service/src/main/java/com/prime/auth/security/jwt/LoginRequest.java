package com.prime.auth.security.jwt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class LoginRequest {

	private final String username;
	private final String password;
	private final String token;
	private final String deviceId;
	private String latitude;
	private String longitude;

	@JsonCreator
	public LoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password,
			@JsonProperty("token") String token, @JsonProperty("deviceId") String deviceId,
			@JsonProperty("longitude") String longitude, @JsonProperty("latitude") String latitude) {
		this.username = username != null ? username.toUpperCase() : null;
		this.password = password;
		this.token = token;
		this.deviceId = deviceId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
