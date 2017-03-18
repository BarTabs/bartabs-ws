package com.bartabs.ws.exceptions;

public class TokenEncodeException extends Exception
{
	private static final long serialVersionUID = 1L;

	public TokenEncodeException(final String message, final Exception ex)
	{
		super(message, ex);
	}
}
