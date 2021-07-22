package com.prime.common.exception;

import java.util.Date;

import com.prime.common.util.DateUtils;

public class SecurityTokenExpiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8955712353055327850L;
	private final Date expiryDate;

	public SecurityTokenExpiredException(String message, Date expiryDate) {
		super(message);
		this.expiryDate = expiryDate;
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();
		if (expiryDate != null) {
			message += " Token expired at : " + DateUtils.formatDate(expiryDate);
		}
		return message;
	}

}
