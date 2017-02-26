package com.bartabs.ws.user.dataaccess;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.bartabs.ws.exceptions.DuplicateUserNameException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.model.User;

public interface UserDao
{

	public User getUserByID(Long objectID) throws UserNotFoundException;

	public User getUserByUserName(String userName) throws UserNotFoundException;

	public Long createUser(User user)
			throws NoSuchAlgorithmException, InvalidKeySpecException, DuplicateUserNameException;

	public void updateUser(User user);

	public void removeUser(User user);

}
