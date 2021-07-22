package com.prime.auth.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.prime.auth.security.jwt.JwtExpiredTokenException;
import com.prime.common.exception.ApiError;
import com.prime.common.exception.BaseAdviseController;
import com.prime.common.exception.InvalidJwtToken;
import com.prime.common.util.Global;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AdviseController extends BaseAdviseController {

	@ExceptionHandler(AuthenticationServiceException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleException(AuthenticationServiceException e) {
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.BAD_CREDENTIALS));
	}

	@ExceptionHandler(InvalidJwtToken.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleException(InvalidJwtToken e) {
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.BAD_CREDENTIALS));
	}

	@ExceptionHandler(JwtExpiredTokenException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<Object> handleException(JwtExpiredTokenException e) {
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.BAD_CREDENTIALS));
	}

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Object> handleException(BadCredentialsException e) {
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.BAD_CREDENTIALS));
	}

	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleException(AccessDeniedException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(
				new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Request Not Allowed," + Global.COMMON_ERROR));
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	protected ResponseEntity<Object> handleException(UsernameNotFoundException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage()));
	}

}
