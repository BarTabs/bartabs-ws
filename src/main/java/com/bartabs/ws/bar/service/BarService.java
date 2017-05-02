package com.bartabs.ws.bar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bartabs.ws.bar.criteria.BarCriteria;
import com.bartabs.ws.bar.dao.BarDao;
import com.bartabs.ws.bar.model.Bar;
import com.bartabs.ws.location.service.LocationService;

@Service("Bar.BarService")
public class BarService {

	@Qualifier("Bar.BarDao")
	@Autowired
	private BarDao dao;

	@Qualifier("Location.LocationService")
	@Autowired
	private LocationService locationService;

	public List<Bar> getBars(BarCriteria criteria) {
		return dao.getBars(criteria);
	}

	public Bar getBar(Long objectID) {
		return dao.getBar(objectID);
	}

	public Long createBar(Bar bar) {
		final Long locationID = locationService.createLocation(bar.getLocation());
		bar.setLocationID(locationID);
		final Long barID = dao.createBar(bar);

		return barID;
	}

	public void updateBar(Bar bar) {
		dao.updateBar(bar);
	}

	public void deleteBar(Long objectID) {
		dao.deleteBar(objectID);
	}

	public void checkIn(Long barID, Long userID) {
		dao.checkIn(barID, userID);
	}
}
