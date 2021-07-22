package com.prime.common.exception;

import java.net.SocketTimeoutException;

import javax.crypto.IllegalBlockSizeException;

import org.hibernate.LazyInitializationException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.prime.common.util.Global;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class BaseAdviseController extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Incorrect data value Provided."));
	}

	@ExceptionHandler(SocketTimeoutException.class)
	protected ResponseEntity<Object> handleException(SocketTimeoutException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.REQUEST_TIMEOUT, Global.COMMON_ERROR));
	}

	@ExceptionHandler(DataAccessResourceFailureException.class)
	protected ResponseEntity<Object> handleException(DataAccessResourceFailureException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.REQUEST_TIMEOUT, Global.COMMON_ERROR));
	}

	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<Object> handleException(NullPointerException ex) {
		log.error("java.lang.NullPointerException:", ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.COMMON_ERROR));
	}

	@ExceptionHandler(ApplicationException.class)
	protected ResponseEntity<Object> handleException(ApplicationException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleException(DataIntegrityViolationException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(
				new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "update or delete record can not be done."));
	}

	@ExceptionHandler(SpelEvaluationException.class)
	protected ResponseEntity<Object> handleException(SpelEvaluationException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(
				new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "update or delete record can not be done."));
	}

	@ExceptionHandler(NoImageFoundException.class)
	protected ResponseEntity<Object> handleException(NoImageFoundException ex) {
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	@ExceptionHandler(SQLGrammarException.class)
	protected ResponseEntity<Object> handleException(SQLGrammarException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.COMMON_ERROR));
	}

	@ExceptionHandler(IllegalBlockSizeException.class)
	protected ResponseEntity<Object> handleException(IllegalBlockSizeException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.COMMON_ERROR));
	}

	@ExceptionHandler(UnexpectedRollbackException.class)
	protected ResponseEntity<Object> handleException(UnexpectedRollbackException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.COMMON_ERROR));
	}

	@ExceptionHandler(LazyInitializationException.class)
	protected ResponseEntity<Object> handleException(LazyInitializationException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.COMMON_ERROR));
	}

	@ExceptionHandler(DataException.class)
	protected ResponseEntity<Object> handleException(DataException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.COMMON_ERROR));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.COMMON_ERROR));
	}

	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	protected ResponseEntity<Object> handleException(InvalidDataAccessResourceUsageException ex) {
		log.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, Global.COMMON_ERROR));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getMessage(), ex);
		BindingResult result = ex.getBindingResult();
		for (FieldError fieldError : result.getFieldErrors()) {
			return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
					"The " + validateName(fieldError.getField()) + " " + fieldError.getDefaultMessage()));
		}
		for (ObjectError fieldError : result.getGlobalErrors()) {
			return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, fieldError.getDefaultMessage()));
		}
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
	}

	private String validateName(String field) {

		if (field.length() > 2) {
			field = field.replace("Id", "");
		}
		return field;
	}

	protected ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
