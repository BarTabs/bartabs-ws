package com.bartabs.ws.authenticate.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bartabs.ws.authenticate.dataaccess.AuthenticateDao;
import com.bartabs.ws.exceptions.InvalidPasswordException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.user.service.UserService;
import com.bartabs.ws.util.PasswordHasher;

@Service("Authenticate.AuthenticateService")
public class AuthenticateService
{

	@Qualifier("Authenticate.AuthenticateDao")
	@Autowired
	private AuthenticateDao authenticateDao;

	@Qualifier("User.UserService")
	@Autowired
	private UserService userService;

	public boolean authenticate(final String inputUsername, final String inputPassword)
			throws UserNotFoundException, InvalidPasswordException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		User user = userService.getUserByUserName(inputUsername);
		if (user != null) {
			String password = user.getPassword();
			String hashedPassword = PasswordHasher.generateStrongPasswordHash(inputPassword);
			if (password.equals(hashedPassword)) {
				return true;
			} else {
				throw new InvalidPasswordException();
			}

		} else {
			throw new UserNotFoundException();
		}
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
