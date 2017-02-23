package com.bartabs.ws.authenticate.controller;

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

@Controller("Authenticate.AuthenticateController")
public class AuthenticateController extends Response
{

	@Autowired
	@Qualifier("Authenticate.AuthenticateService")
	private AuthenticateService authenticateService;

	@Autowired
	@Qualifier("Authenticate.TokenService")
	private TokenService tokenService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response authenticate(final AuthenticateParameters authenticationParams) throws Exception
	{

		try {
			final String username = authenticationParams.getUsername();
			final String password = authenticationParams.getPassword();

			String token = null;

			if (authenticateService.authenticate(username, password)) {
				token = tokenService.encodeToken(username);
			}

			return buildResponse(token);

		} catch (UserNotFoundException ex) {

			return buildErrorResponse(ex.getMessage());

		} catch (InvalidPasswordException ex) {

			return buildErrorResponse(ex.getMessage());
		}
	}
}
