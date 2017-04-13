package com.bartabs.ws.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.Response;
import com.bartabs.ws.authenticate.service.TokenService;
import com.bartabs.ws.exceptions.DuplicateUserNameException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.user.service.UserService;

@Controller("User.UserController")
public class UserController extends Response
{

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier("User.UserService")
	private UserService service;

	@Autowired
	@Qualifier("Authenticate.TokenService")
	private TokenService tokenService;

	@RequestMapping(value = "/user/getuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response authenticate(@RequestParam("token") final String token)
	{

		try {
			log.debug("Getting user: " + token);

			User user = service.getUserByToken(token);
			return buildResponse(user);

		} catch (UserNotFoundException ex) {
			log.error(ex.toString(), ex);

			return buildErrorResponse("User not found.");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);

			return buildErrorResponse("Illegal authorization token. Please sign out and try again.");
		}

	}

	@RequestMapping(value = "/user/createuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response createUser(@RequestBody final User user)
	{
		try {
			Long userID = service.createUser(user);
			final User newUser = service.getUserByID(userID);
			final String token = tokenService.encodeToken(newUser.getUsername());
			newUser.setToken(token);

			final String uuid = service.refreshUuid(userID);
			newUser.setUuid(uuid);

			return buildResponse(newUser);
		} catch (DuplicateUserNameException ex) {
			return buildErrorResponse(ex.getMessage());
		} catch (UserNotFoundException ex) {
			return buildErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			return buildErrorResponse("Error processing user creation");
		}
	}

	@RequestMapping(value = "/user/updateuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response updateUser(@RequestBody final User user)
	{
		try {
			User updatedUser;
			updatedUser = service.updateUser(user);

			return buildResponse(updatedUser);
		} catch (UserNotFoundException e) {

			return buildErrorResponse(e.getMessage());
		}

	}

	@RequestMapping(value = "/user/deleteuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response deleteUser(@RequestBody final User user)
	{
		service.removeUser(user);
		return buildResponse("Ok");
	}

	@RequestMapping(value = "/user/registerfornotifications", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response registerForNotifications(HttpServletRequest request,
			@RequestParam("fcmToken") String fcmToken)
	{
		try {
			final String authToken = request.getHeader("authorization");
			User user = service.getUserByToken(authToken);

			service.registerForNotifications(user.getObjectID(), fcmToken);
			return buildResponse("Successfully registered token");
		} catch (Exception ex) {
			log.error(ex.toString());
			return buildErrorResponse("Error registering for notifications");
		}
	}
}
