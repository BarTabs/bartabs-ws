package com.bartabs.ws.user.dataaccess;

import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;

public interface UserDao
{

	public User getUserByID(Long objectID) throws UserNotFoundException;

	public User getUserByUserName(String userName) throws UserNotFoundException;

	public Long createUser(User user);

	public void updateUser(User user);

	public void removeUser(User user);

}
