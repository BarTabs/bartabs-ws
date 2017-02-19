package com.bartabs.ws.exceptions;

public class TokenEncodeException extends Exception
{
	public TokenEncodeException(final String message, final Exception ex)
	{
		super(message, ex);
	}
}
