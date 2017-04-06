package com.bartabs.ws.exceptions;

public class PasswordMissingException extends Exception {
	private static final long serialVersionUID = 1L;

	public PasswordMissingException() {
		super("User's must define a password before creation.");
	}
}
