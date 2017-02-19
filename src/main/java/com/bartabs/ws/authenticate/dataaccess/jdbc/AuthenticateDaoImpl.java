package com.bartabs.ws.authenticate.dataaccess.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.authenticate.dataaccess.AuthenticateDao;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.dataaccess.UserDao;
import com.bartabs.ws.user.model.User;

@Repository("Authenticate.AuthenticateDao")
public class AuthenticateDaoImpl implements AuthenticateDao
{
	@Autowired
	NamedParameterJdbcTemplate template;

	@Qualifier("User.UserDao")
	@Autowired
	private UserDao userDao;

	public String generateToken(Long userID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByToken(String token) throws UserNotFoundException
	{
		try {
			// @formatter:off
			String sql = ""
				+ "SELECT user_id "
				+ "FROM bartabs.user_token "
				+ "WHERE token = :token";
			// @formatter:on

			Long userID = template.queryForObject(sql, new MapSqlParameterSource("token", token), Long.class);

			return userDao.getUserByID(userID);

		} catch (EmptyResultDataAccessException ex) {
			return null;
		}

	}
}
