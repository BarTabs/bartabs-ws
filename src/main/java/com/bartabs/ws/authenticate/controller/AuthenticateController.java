/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.authenticate.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.Response;
import com.bartabs.ws.authenticate.model.AuthenticateParameters;
import com.bartabs.ws.authenticate.service.AuthenticateService;
import com.bartabs.ws.authenticate.service.TokenService;
import com.bartabs.ws.exceptions.InvalidPasswordException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;

/**
 * The {@code AuthenticateController} class defines the API routes available to
 * the users for authentication
 * 
 * @author Victor Lora
 * @version 1.0
 * @see com.bartabs.ws.authenticate.model.AuthenticateParameters
 * @see com.bartabs.ws.authenticate.service.AuthenticateService
 * @see com.bartabs.ws.authenticate.service.TokenService
 * @since 2014-04-12
 *
 */
@Controller("Authenticate.AuthenticateController")
public class AuthenticateController extends Response
{

	@Autowired
	@Qualifier("Authenticate.AuthenticateService")
	private AuthenticateService authenticateService;

	@Autowired
	@Qualifier("Authenticate.TokenService")
	private TokenService tokenService;

	/**
	 * Route by which a user is authenticated when they attempt to log in
	 * 
	 * @param authenticationParams
	 *            contains the parameters necessary to authenticate
	 * @return a JSON response containing a {@code User} model with the
	 *         corresponding user information
	 * @throws Exception
	 *             throws exception when a user is not found, a database error
	 *             occurs, or invalid credentials are provided
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response authenticate(final AuthenticateParameters authenticationParams) throws Exception
	{

		try {
			final String username = authenticationParams.getUsername();
			final String password = authenticationParams.getPassword();

			String token = null;

			final User user = authenticateService.authenticate(username, password);

			if (Objects.equals(user.getAuthenticated(), true)) {
				token = tokenService.encodeToken(username);
				user.setToken(token);
			}

			return buildResponse(user);

		} catch (UserNotFoundException ex) {

			return buildErrorResponse(ex.getMessage());

		} catch (InvalidPasswordException ex) {

			return buildErrorResponse(ex.getMessage());
		}
	}
}
