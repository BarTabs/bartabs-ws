package com.bartabs.ws.exceptions;

public class UserNotFoundException extends Exception {

	public UserNotFoundException() {
		super("User not found");
	};

}
