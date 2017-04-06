package com.bartabs.ws.exceptions;

public class MissingUsernameException extends Exception {
	private static final long serialVersionUID = 1L;

	public MissingUsernameException() {
		super("User's must define a username before creation.");
	}
}
