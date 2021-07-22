package com.prime.common.exception;

public class NoImageFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9185356056255639684L;

	static String ERROR = "No Image found";

	public NoImageFoundException() {
		super(ERROR);
	}

}
