package com.main;

public class InvalidZipCodeException extends Exception{

	private static final long serialVersionUID = 1L;

	/*
	 * Default Constructor
	 */
	public InvalidZipCodeException() {
		
	}
	
	/*
	 * Constructor with customized message
	 */
	public InvalidZipCodeException(String message) {
		super(message);
	}
}

