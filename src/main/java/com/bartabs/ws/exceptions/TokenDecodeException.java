package com.bartabs.ws.exceptions;

public class TokenDecodeException extends Exception
{
	public TokenDecodeException(final String message, final Exception ex)
	{
		super(message, ex);
	}
}
