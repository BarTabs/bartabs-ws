package com.bartabs.ws.exceptions;

public class TokenDecodeException extends Exception
{
	private static final long serialVersionUID = 1L;

	public TokenDecodeException(final String message, final Exception ex)
	{
		super(message, ex);
	}
}
