package com.bartabs.ws.user.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.authenticate.service.AuthenticateService;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.user.service.UserService;

public class UserController
{

	private final Log log = LogFactory.getLog(this.getClass());

	@Qualifier("User.UserService")
	@Autowired
	private UserService service;

	@Qualifier("Authenticate.AuthenticateService")
	@Autowired
	private AuthenticateService authenticateService;

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
	public @ResponseBody User createUser(final User user)
	{
		Long userID = service.createUser(user);

		return service.getUserByID(userID);
	}

	@RequestMapping(value = "/user/updateuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User updateUser(final User user)
	{
		User updatedUser = service.updateUser(user);

		return updatedUser;
	}

	@RequestMapping(value = "/user/deleteuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User deleteUser(final User user)
	{
		service.removeUser(user);

		return null;
	}
}
