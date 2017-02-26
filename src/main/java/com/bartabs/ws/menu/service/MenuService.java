package com.bartabs.ws.menu.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bartabs.ws.exceptions.MenuNotFoundException;
import com.bartabs.ws.location.service.LocationService;
import com.bartabs.ws.menu.dataaccess.MenuDao;
import com.bartabs.ws.menu.model.Menu;

@Service("Menu.MenuService")
public class MenuService
{

	@Qualifier("Menu.MenuDao")
	@Autowired
	private MenuDao dao;

	@Qualifier("Location.LocationService")
	@Autowired
	private LocationService locationService;

	public Menu getMenuByBarID(final Long barID) throws MenuNotFoundException
	{
		Menu menu = dao.getMenuByBarID(barID);

		return menu;
	}

	public Menu getMenuByID(final Long menuID) throws MenuNotFoundException
	{
		Menu menu = dao.getMenuByID(menuID);

		return menu;
	}

	public Long createMenu(final Menu menu) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		Long menuID = dao.createMenu(menu);

		return menuID;
	}

	public Menu updateMenu(final Menu menu) throws MenuNotFoundException
	{

		dao.updateMenu(menu);
		Menu updatedMenu = dao.getMenuByID(menu.getObjectID());

		return updatedMenu;
	}

	public void removeMenu(final Long menuID)
	{
		dao.removeMenu(menuID);
	}

}
