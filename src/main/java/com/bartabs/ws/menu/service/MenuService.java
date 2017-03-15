package com.bartabs.ws.menu.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bartabs.ws.exceptions.MenuNotFoundException;
import com.bartabs.ws.location.service.LocationService;
import com.bartabs.ws.menu.criteria.MenuCriteria;
import com.bartabs.ws.menu.dataaccess.MenuDao;
import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.model.MenuItem;

@Service("Menu.MenuService")
@Transactional("txManager")
public class MenuService
{

	@Qualifier("Menu.MenuDao")
	@Autowired
	private MenuDao dao;

	@Qualifier("Location.LocationService")
	@Autowired
	private LocationService locationService;

	public Menu getMenu(final MenuCriteria criteria)
	{
		Menu menu = dao.getMenuByBarID(criteria.getBarID());
		Long menuID = menu.getObjectID();
		criteria.setMenuID(menuID);

		List<MenuItem> menuItems = getMenuItems(criteria);
		menu.setMenuItems(menuItems);

		return menu;
	}

	public Menu getMenuByBarID(final Long barID) throws MenuNotFoundException
	{
		MenuCriteria criteria = new MenuCriteria();
		criteria.setBarID(barID);

		return getMenu(criteria);
	}

	public Menu getMenuByID(final Long menuID) throws MenuNotFoundException
	{
		Menu menu = dao.getMenuByID(menuID);

		final MenuCriteria criteria = new MenuCriteria();
		criteria.setMenuID(menuID);

		return getMenuByBarID(menu.getBarID());
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

	public void createMenuItems(final MenuItem menuItem)
	{
		dao.createMenuItem(menuItem);
	}

	public void updateMenuItems(final MenuItem menuItem)
	{
		dao.updateMenuItem(menuItem);
	}

	public List<MenuItem> getMenuItems(final MenuCriteria criteria)
	{
		return dao.getMenuItems(criteria);
	}

	public List<String> getCategories(final MenuCriteria criteria)
	{
		return dao.getCategories(criteria);
	}

	public List<String> getTypes(final MenuCriteria criteria)
	{
		return dao.getTypes(criteria);
	}

	public MenuItem getMenuItemByID(Long objectID)
	{
		final MenuItem menuItem = dao.getMenuItemsByID(objectID);
		// TODO: Add menu ingredients

		return menuItem;
	}

	public MenuItem createMenuItem(MenuItem menuItem)
	{
		Long menuItemID = dao.createMenuItem(menuItem);
		MenuItem newMenuItem = dao.getMenuItemsByID(menuItemID);

		return newMenuItem;
	}

	public MenuItem updateMenuItem(MenuItem menuItem)
	{
		dao.updateMenuItem(menuItem);
		MenuItem updatedMenuItem = dao.getMenuItemsByID(menuItem.getObjectID());

		return updatedMenuItem;
	}

	public void removeMenuItem(Long menuItemID)
	{
		dao.removeMenuItem(menuItemID);
	}

}
