package com.bartabs.ws.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.location.model.Location;
import com.bartabs.ws.location.service.LocationService;
import com.bartabs.ws.user.dataaccess.UserDao;
import com.bartabs.ws.user.model.User;

@Service("User.UserService")
public class UserService
{

	@Qualifier("User.UserDao")
	@Autowired
	private UserDao dao;

	@Qualifier("Location.LocationService")
	@Autowired
	private LocationService locationService;

	public User getUserByID(final Long userID) throws UserNotFoundException
	{
		User user = dao.getUserByID(userID);

		return user;
	}

	public User getUserByUserName(final String userName) throws UserNotFoundException
	{
		User user = dao.getUserByUserName(userName);

		return user;
	}

	public Long createUser(final User user)
	{
		Location location = user.getLocation();

		if (location != null) {
			Long locationID = locationService.createLocation(location);
			location.setObjectID(locationID);
		}

		Long userID = dao.createUser(user);

		return userID;
	}

	public User updateUser(final User user) throws UserNotFoundException
	{
		Location location = user.getLocation();

		if (location != null) {
			locationService.updateLocation(location);
		}

		dao.updateUser(user);
		User updatedUser = dao.getUserByID(user.getObjectID());

		return updatedUser;
	}

	public void removeUser(final User user)
	{
		dao.removeUser(user);
	}

}
