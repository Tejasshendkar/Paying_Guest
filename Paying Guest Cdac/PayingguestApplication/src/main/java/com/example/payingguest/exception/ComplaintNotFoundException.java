package com.example.payingguest.exception;

public class ComplaintNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3132468824804046224L;

	public ComplaintNotFoundException(String msg) {
		super(msg);
	}
}
