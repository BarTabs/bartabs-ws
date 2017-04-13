package com.bartabs.ws.exceptions;

public class UserNotFoundException extends Exception
{
	private static final long serialVersionUID = 1L;

	public UserNotFoundException()
	{
		super("User not found");
	}

	public UserNotFoundException(String customMessage)
	{
		super(customMessage);
	}

}
