package com.prime.common.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.prime.common.enums.ErrorCode;

public class ErrorResponse {

	// HTTP Response Status Code
	private final HttpStatus status;

	// General Error message
	private final String message;

	// Error code
	private final ErrorCode errorCode;

	private final Date timestamp;

	protected ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
		this.message = message;
		this.errorCode = errorCode;
		this.status = status;
		this.timestamp = new Date();
	}

	public ErrorResponse(HttpStatus status, String message) {
		this.message = message;
		this.status = status;
		this.timestamp = new Date();
		this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
	}

	public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
		return new ErrorResponse(message, errorCode, status);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
