package com.bartabs.ws.location.dataaccess;

import com.bartabs.ws.location.model.Location;

public interface LocationDao
{
	public Location getLocationByID(Long locationID);

	public Long createLocation(Location location);

	public void updateLocation(Location location);

	public void removeLocation(Location location);

}
