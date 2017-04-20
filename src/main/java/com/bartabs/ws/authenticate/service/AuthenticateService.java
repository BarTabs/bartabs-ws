/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.authenticate.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bartabs.ws.exceptions.InvalidPasswordException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.user.service.UserService;
import com.bartabs.ws.util.PasswordHasher;

/**
 * The {@code AuthenticateService} class provides the methods and database
 * access necessary for a user to login
 * 
 * @author Victor Lora
 * @version 1.0
 * @since 2014-04-12
 *
 */
@Service("Authenticate.AuthenticateService")
@Transactional("txManager")
public class AuthenticateService
{
	@Qualifier("User.UserService")
	@Autowired
	private UserService userService;

	/**
	 * Authenticates a user by taking the user name and password values
	 * submitted by the user against the user names and hashed password found in
	 * the database
	 * 
	 * @param inputUsername
	 *            a {@code String} containing the user name to be authenticated
	 * @param inputPassword
	 *            a {@code String} containing the password to be authenticated
	 * @return the appropriate {@code User} if authenticated; null if not found
	 *         if or credentials were incorrect.
	 * @throws UserNotFoundException
	 *             thrown when user is not present in the database
	 * @throws InvalidPasswordException
	 *             thrown when an invalid password is provided
	 * @throws NoSuchAlgorithmException
	 *             thrown when the algorithm specified by the JWT token does not
	 *             exist
	 * @throws InvalidKeySpecException
	 *             thrown when an invalid JWT token is provided
	 */
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

				String uuid = userService.refreshUuid(user.getObjectID());
				user.setUuid(uuid);

				return user;
			} else {
				throw new InvalidPasswordException();
			}

		} else {
			throw new UserNotFoundException();
		}
	}

}
