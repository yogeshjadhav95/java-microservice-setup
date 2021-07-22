package com.prime.common.exception;

public class SecurityRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7303766612112231256L;

	public SecurityRuntimeException() {
		super();
	}

	public SecurityRuntimeException(String message) {
		super(message);
	}

	public SecurityRuntimeException(Throwable cause) {
		super(cause);
	}

	public SecurityRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
