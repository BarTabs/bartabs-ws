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

@Controller("Authenticate.AuthenticateController")
public class AuthenticateController
{
	private static final String AUTHENTICATION_ERROR_MESSAGE = "Invalid authentication attempt.";

	@Autowired
	@Qualifier("Authenticate.AuthenticateService")
	private AuthenticateService authenticateService;

	@Autowired
	@Qualifier("Authenticate.TokenService")
	private TokenService tokenService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response authenticate(final AuthenticateParameters authenticationParams) throws Exception
	{

		final String username = authenticationParams.getUsername();
		final String password = authenticationParams.getPassword();

		final Response response = new Response();
		if (authenticateService.authenticate(username, password)) {
			// final String token = tokenService.encodeToken(username);
			final String token = "test";
			response.setData(token);
		} else {
			response.setStatus(401);
			response.setMessage(AUTHENTICATION_ERROR_MESSAGE);
		}

		return response;
	}
}
