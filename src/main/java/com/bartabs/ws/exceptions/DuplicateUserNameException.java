package com.bartabs.ws.exceptions;

public class DuplicateUserNameException extends Exception
{
	private static final long serialVersionUID = 1L;

	public DuplicateUserNameException()
	{
		super("Username not available. Please try again with a different username.");
	}
}
