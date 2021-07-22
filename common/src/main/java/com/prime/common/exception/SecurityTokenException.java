package com.prime.common.exception;

public class SecurityTokenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1175000857171068178L;

	public SecurityTokenException() {
	}

	public SecurityTokenException(String message) {
		super(message);
	}

	public SecurityTokenException(Throwable cause) {
		super(cause);
	}

	public SecurityTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityTokenException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
