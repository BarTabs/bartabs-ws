package com.bartabs.ws.bar.dao;

import java.util.List;

import com.bartabs.ws.bar.criteria.BarCriteria;
import com.bartabs.ws.bar.model.Bar;

public interface BarDao {
	public List<Bar> getBars(BarCriteria criteria);

	public Bar getBar(Long objectID);

	public void createBar(Bar bar);

	public void updateBar(Bar bar);

	public void deleteBar(Long objectID);

	public void checkIn(Long barID, Long userID);

}
