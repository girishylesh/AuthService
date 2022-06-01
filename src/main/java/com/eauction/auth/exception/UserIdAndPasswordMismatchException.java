package com.eauction.auth.exception;


public class UserIdAndPasswordMismatchException extends Exception {
	
	private static final long serialVersionUID = -4561734448611020635L;

	public UserIdAndPasswordMismatchException(String message) {
        super(message);
    }
}
