package com.bartabs.ws.authenticate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bartabs.ws.authenticate.dataaccess.AuthenticateDao;
import com.bartabs.ws.exceptions.InvalidPasswordException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.user.service.UserService;

@Service("Authenticate.AuthenticateService")
public class AuthenticateService
{

	@Qualifier("Authenicate.AuthenticateDao")
	@Autowired
	private AuthenticateDao authenticateDao;

	@Qualifier("User.UserService")
	@Autowired
	private UserService userService;

	public String authenticate(final String inputUsername, String inputPassword)
			throws UserNotFoundException, InvalidPasswordException
	{
		String token = null;
		User user = userService.getUserByUserName(inputUsername);
		if (user != null) {
			String password = user.getPassword();

			if (password.equals(inputPassword)) {
				Long userID = user.getObjectID();
				token = authenticateDao.generateToken(userID);
			} else {
				throw new InvalidPasswordException();
			}

		} else {
			throw new UserNotFoundException();
		}

		return token;
	}

	public User getUserByToken(final String token) throws UserNotFoundException
	{
		User user = authenticateDao.getUserByToken(token);

		if (user == null) {
			throw new UserNotFoundException();
		}

		return user;
	}

}
