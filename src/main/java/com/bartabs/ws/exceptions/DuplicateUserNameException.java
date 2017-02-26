package com.bartabs.ws.exceptions;

public class DuplicateUserNameException extends Exception
{
	public DuplicateUserNameException()
	{
		super("Username not available. Please try again with a different username.");
	}
}
