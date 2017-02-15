package com.bartabs.ws.user.model;

public enum UserType
{
	ADMIN(0), OWNER(1), EMPLOYEE(2), BARTENDER(3), CUSTOMER(4);

	private int type;

	UserType(int type)
	{
		this.type = type;
	}

	int getType()
	{
		return type;
	}
}
