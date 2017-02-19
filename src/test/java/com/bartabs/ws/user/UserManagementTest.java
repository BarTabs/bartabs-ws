package com.bartabs.ws.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bartabs.ws.user.service.UserService;

public class UserManagementTest
{

	@Qualifier("User.UserService")
	@Autowired
	private UserService service;

	@Test
	public void testUserCreate()
	{
	}

}
