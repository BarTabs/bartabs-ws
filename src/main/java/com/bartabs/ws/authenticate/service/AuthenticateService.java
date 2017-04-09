package com.bartabs.ws.authenticate.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bartabs.ws.authenticate.dataaccess.AuthenticateDao;
import com.bartabs.ws.exceptions.InvalidPasswordException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.user.service.UserService;
import com.bartabs.ws.util.PasswordHasher;

@Service("Authenticate.AuthenticateService")
@Transactional("txManager")
public class AuthenticateService
{

	@Qualifier("Authenticate.AuthenticateDao")
	@Autowired
	private AuthenticateDao authenticateDao;

	@Qualifier("User.UserService")
	@Autowired
	private UserService userService;

	public User authenticate(final String inputUsername, final String inputPassword)
			throws UserNotFoundException, InvalidPasswordException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		User user = userService.getUserByUserName(inputUsername);
		if (user != null) {
			String password = user.getPassword();
			byte[] salt = user.getSalt();
			String hashedPassword = PasswordHasher.generateStrongPasswordHash(inputPassword, salt);
			if (password.equals(hashedPassword)) {
				user.setAuthenticated(true);
				user.setPassword(null);
				user.setSalt(null);
				return user;
			} else {
				throw new InvalidPasswordException();
			}

		} else {
			throw new UserNotFoundException();
		}
	}

}
