package com.prime.auth.service;

import java.util.Date;
import java.util.Optional;

import com.prime.auth.projection.AuthUserProjection;

public interface UserService {

	Optional<AuthUserProjection> getAuthUserByLoginId(String username);

	void updateFailedAttemptes(String id, int failed);

	void lockUser(String id);

	void updateUserLastLoginDetails(Date date, String clientIp, String id, String deviceId, String latitude,
			String longitude);

}
