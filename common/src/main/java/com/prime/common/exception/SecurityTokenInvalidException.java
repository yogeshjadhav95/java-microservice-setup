package com.prime.common.exception;

public class SecurityTokenInvalidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -142332524441893110L;

	public SecurityTokenInvalidException() {
	}

	public SecurityTokenInvalidException(String message) {
		super(message);
	}

	public SecurityTokenInvalidException(Throwable cause) {
		super(cause);
	}

	public SecurityTokenInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityTokenInvalidException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
