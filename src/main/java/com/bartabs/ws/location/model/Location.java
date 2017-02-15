package com.bartabs.ws.location.model;

public class Location
{
	private Long objectID;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private int zipCode;
	private Long geoAreaID;

	public Long getObjectID()
	{
		return objectID;
	}

	public void setObjectID(Long objectID)
	{
		this.objectID = objectID;
	}

	public String getAddress1()
	{
		return address1;
	}

	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public int getZipCode()
	{
		return zipCode;
	}

	public void setZipCode(int zipCode)
	{
		this.zipCode = zipCode;
	}

	public Long getGeoAreaID()
	{
		return geoAreaID;
	}

	public void setGeoAreaID(Long geoAreaID)
	{
		this.geoAreaID = geoAreaID;
	}

}
