package com.hcl.ecommerce.exceptions;

public class ValidationExceptionHandler extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3445379582808748497L;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public ValidationExceptionHandler(String message) {
		super();
		this.message=message;
	}

}
