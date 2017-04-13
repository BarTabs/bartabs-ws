package com.bartabs.ws.user.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bartabs.ws.authenticate.service.TokenService;
import com.bartabs.ws.exceptions.DuplicateUserNameException;
import com.bartabs.ws.exceptions.MissingUsernameException;
import com.bartabs.ws.exceptions.PasswordMissingException;
import com.bartabs.ws.exceptions.TokenDecodeException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.location.model.Location;
import com.bartabs.ws.location.service.LocationService;
import com.bartabs.ws.user.dataaccess.UserDao;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.util.PasswordHasher;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Service("User.UserService")
@Transactional("txManager")
public class UserService
{

	@Qualifier("User.UserDao")
	@Autowired
	private UserDao dao;

	@Qualifier("Location.LocationService")
	@Autowired
	private LocationService locationService;

	@Qualifier("Authenticate.TokenService")
	@Autowired
	private TokenService tokenService;

	public User getUserByID(final Long userID) throws UserNotFoundException
	{
		if (userID != null) {
			User user = dao.getUserByID(userID);

			return user;
		} else {
			throw new UserNotFoundException("No user ID provided.");
		}
	}

	public User getUserByUserName(final String userName) throws UserNotFoundException
	{
		User user = dao.getUserByUserName(userName);

		return user;
	}

	public User getUserByToken(final String token) throws UserNotFoundException, TokenDecodeException
	{
		Jws<Claims> claims = tokenService.decodeToken(token);
		String username = claims.getBody().getSubject();
		User user = dao.getUserByUserName(username);

		if (user == null) {
			throw new UserNotFoundException();
		}

		return user;
	}

	public Long createUser(final User user) throws NoSuchAlgorithmException, InvalidKeySpecException,
			DuplicateUserNameException, PasswordMissingException, MissingUsernameException
	{
		Long userID = null;
		Location location = user.getLocation();

		if (location != null) {
			Long locationID = locationService.createLocation(location);
			location.setObjectID(locationID);
		}

		if (user.getUsername() != null) {
			if (user.getPassword() != null) {
				final byte[] salt = PasswordHasher.getSalt();
				user.setSalt(salt);
				final String hashedPassword = PasswordHasher.generateStrongPasswordHash(user.getPassword(), salt);
				user.setPassword(hashedPassword);
				userID = dao.createUser(user);
			} else {
				throw new PasswordMissingException();
			}
		} else {
			throw new MissingUsernameException();
		}

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

	public void registerForNotifications(Long userID, String fcmToken)
	{
		dao.registerForFcmNotifications(userID, fcmToken);
	}

	public String retrieveFcmRegistrationToken(Long userID)
	{
		return dao.retrieveFcmRegistrationToken(userID);
	}

	public String refreshUuid(Long userID)
	{
		return dao.refreshUuid(userID);
	}

	public User getUserFromUuid(String uuid) throws UserNotFoundException
	{
		User user = dao.getUserFromUuid(uuid);
		return user;
	}
}
