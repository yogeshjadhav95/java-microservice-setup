package com.prime.common.library;

import java.util.Date;

import com.jsoniter.annotation.JsonIgnore;
import com.prime.common.enums.Status;

public class LoginUserDetails {

	protected String id;

	protected String loginId;

	@JsonIgnore
	protected String password;

	protected String timeZone;

	protected String langCode;

	protected String currency;

	protected String deviceId;

	protected Integer decimalScale;

	@JsonIgnore
	protected String loginIpAddress;

	@JsonIgnore
	protected Date lastPasswordChangedDate;

	@JsonIgnore
	protected Boolean locked = false;

	@JsonIgnore
	protected Boolean deleted = false;

	@JsonIgnore
	protected Integer failedAttempts;

	@JsonIgnore
	protected Date lastLoginTime;

	@JsonIgnore
	protected Date lastFailedLoginTime;

	@JsonIgnore
	protected Status status;

	protected String tenantType;

	protected String tenantId;

	protected String latitude;

	protected String longitude;

	public void setLoginId(String loginId) {
		if (loginId != null) {
			this.loginId = loginId.toUpperCase();
		}
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public Integer getDecimalScale() {
		return decimalScale;
	}

	public void setDecimalScale(Integer decimalScale) {
		this.decimalScale = decimalScale;
	}

	public String getLoginIpAddress() {
		return loginIpAddress;
	}

	public void setLoginIpAddress(String loginIpAddress) {
		this.loginIpAddress = loginIpAddress;
	}

	public Date getLastPasswordChangedDate() {
		return lastPasswordChangedDate;
	}

	public void setLastPasswordChangedDate(Date lastPasswordChangedDate) {
		this.lastPasswordChangedDate = lastPasswordChangedDate;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(Integer failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastFailedLoginTime() {
		return lastFailedLoginTime;
	}

	public void setLastFailedLoginTime(Date lastFailedLoginTime) {
		this.lastFailedLoginTime = lastFailedLoginTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getTenantType() {
		return tenantType;
	}

	public void setTenantType(String tenantType) {
		this.tenantType = tenantType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}
