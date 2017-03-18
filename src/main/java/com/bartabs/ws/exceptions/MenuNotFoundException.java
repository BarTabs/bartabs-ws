package com.bartabs.ws.exceptions;

public class MenuNotFoundException extends Exception
{
	private static final long serialVersionUID = 1L;

	public MenuNotFoundException(final String message, final Exception ex)
	{
		super(message, ex);
	}
}
