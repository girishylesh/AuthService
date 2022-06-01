package com.eauction.auth.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -6901919106090299797L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
