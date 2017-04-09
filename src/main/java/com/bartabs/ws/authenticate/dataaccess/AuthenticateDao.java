package com.bartabs.ws.authenticate.dataaccess;

public interface AuthenticateDao
{
	public String generateToken(Long userID);
}
