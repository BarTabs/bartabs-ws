package com.bartabs.ws.authenticate.dataaccess.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.authenticate.dataaccess.AuthenticateDao;
import com.bartabs.ws.user.dataaccess.UserDao;

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

}
