package com.bartabs.ws.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bartabs.ws.location.dataaccess.LocationDao;
import com.bartabs.ws.location.model.Location;

public class LocationService
{
	@Qualifier("Location.LocationDao")
	@Autowired
	private LocationDao dao;

	public Location getLocationByID(final Long locationID)
	{
		return dao.getLocationByID(locationID);
	}

	public Long createLocation(final Location location)
	{
		return dao.createLocation(location);
	}

	public void updateLocation(final Location location)
	{
		dao.updateLocation(location);
	}

	public void removeLocation(final Location location)
	{
		dao.removeLocation(location);
	}
}
