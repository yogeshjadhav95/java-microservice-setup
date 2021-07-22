package com.prime.auth.projection;

import java.util.Date;

import com.prime.common.enums.Status;

public interface AuthUserProjection {

	String getId();

	String getLoginId();

	String getPassword();

	String getCurrency();

	String getDeviceId();

	String getTimeZone();

	Integer getDecimalScale();

	Date getLastPasswordChangedDate();

	Boolean getLocked();

	Boolean getDeleted();

	Status getStatus();

	String getTenantType();

	String getTenantId();

	String getLatitude();

	String getLongitude();

	String getLangCode();

	Integer getFailedAttempts();

}
