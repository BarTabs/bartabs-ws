package com.bartabs.ws.user.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.Response;
import com.bartabs.ws.authenticate.service.AuthenticateService;
import com.bartabs.ws.authenticate.service.TokenService;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.user.service.UserService;

@Controller("User.UserController")
public class UserController
{

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier("User.UserService")
	private UserService service;

	@Autowired
	@Qualifier("Authenticate.AuthenticateService")
	private AuthenticateService authenticateService;

	@Autowired
	@Qualifier("Authenticate.TokenService")
	private TokenService tokenService;

	@RequestMapping(value = "/user/getuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User authenticate(@RequestParam("token") final String token)
	{

		try {
			log.debug("Getting user: " + token);

			return authenticateService.getUserByToken(token);

		} catch (UserNotFoundException ex) {
			log.error(ex.toString(), ex);

			return null;
		}

	}

	@RequestMapping(value = "/user/createuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response createUser(final User user)
	{
		try {
			Long userID = service.createUser(user);
			final User newUser = service.getUserByID(userID);
			final String token = tokenService.encodeToken(newUser.getUsername());

			Response response = new Response();
			response.setStatus(200);
			response.setData(token);

			return response;
		} catch (UserNotFoundException ex) {
			Response response = new Response();
			response.setStatus(500);
			response.setMessage(ex.getMessage());

			return response;
		} catch (Exception ex) {
			Response response = new Response();
			response.setStatus(500);
			response.setMessage("Error processing user creation");

			return response;
		}
	}

	@RequestMapping(value = "/user/updateuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response updateUser(final User user)
	{
		try {
			User updatedUser;
			updatedUser = service.updateUser(user);

			Response response = new Response();
			response.setStatus(200);
			response.setData(updatedUser);

			return response;
		} catch (UserNotFoundException e) {
			Response response = new Response();
			response.setMessage(e.getMessage());

			return response;
		}

	}

	@RequestMapping(value = "/user/deleteuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User deleteUser(final User user)
	{
		service.removeUser(user);

		return null;
	}
}
