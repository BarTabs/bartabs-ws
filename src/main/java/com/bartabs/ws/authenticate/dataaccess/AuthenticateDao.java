package com.bartabs.ws.authenticate.dataaccess;

import com.bartabs.ws.user.model.User;

public interface AuthenticateDao
{
	public String generateToken(Long userID);

	public User getUserByToken(String token);
}
