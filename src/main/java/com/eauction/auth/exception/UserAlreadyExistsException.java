package com.eauction.auth.exception;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -2537529811891323985L;

	public UserAlreadyExistsException(String message) {
        super(message);
    }
}
