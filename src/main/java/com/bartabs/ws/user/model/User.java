package com.bartabs.ws.user.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.bartabs.ws.location.model.Location;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User
{
	private Long objectID;
	private String firstName;
	private String lastName;
	private String middleInitial;
	private String formattedName;
	private String phoneNumber;
	private Integer userType;
	private String username;
	private String password;
	private byte[] salt;
	private Date createdTimestamp;
	private Date modifiedTimestamp;

	private Location location;

	public Long getObjectID()
	{
		return objectID;
	}

	public void setObjectID(Long objectID)
	{
		this.objectID = objectID;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMiddleInitial()
	{
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial)
	{
		this.middleInitial = middleInitial;
	}

	public String getFormattedName()
	{
		return formattedName;
	}

	public void setFormattedName(String formattedName)
	{
		this.formattedName = formattedName;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Integer getUserType()
	{
		return userType;
	}

	public void setUserType(Integer userType)
	{
		this.userType = userType;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public byte[] getSalt()
	{
		return salt;
	}

	public void setSalt(byte[] salt)
	{
		this.salt = salt;
	}

	public Date getCreatedTimestamp()
	{
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp)
	{
		this.createdTimestamp = createdTimestamp;
	}

	public Date getModifiedTimestamp()
	{
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp)
	{
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation(Location location)
	{
		this.location = location;
	}

}
