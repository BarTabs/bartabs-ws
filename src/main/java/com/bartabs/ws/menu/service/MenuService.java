package com.bartabs.ws.menu.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bartabs.ws.exceptions.MenuNotFoundException;
import com.bartabs.ws.location.service.LocationService;
import com.bartabs.ws.menu.dataaccess.MenuDao;
import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.model.MenuCategory;
import com.bartabs.ws.menu.model.MenuItem;
import com.bartabs.ws.menu.model.MenuType;

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

	public Menu getMenuByBarID(final Long barID) throws MenuNotFoundException
	{
		Menu menu = dao.getMenuByBarID(barID);
		Long menuID = menu.getObjectID();

		List<MenuCategory> menuCategories = new ArrayList<MenuCategory>();
		List<String> categories = dao.getCategories(menuID);

		// Get all categories under this menu
		for (String category : categories) {
			List<MenuType> menuTypes = new ArrayList<MenuType>();
			List<String> types = dao.getTypes(menuID, category);

			// Get all types under this category
			for (String type : types) {
				// Get all menu items under this menu category and type
				List<MenuItem> menuItems = dao.getMenuItems(menuID, category, type);

				MenuType menuType = new MenuType();
				menuType.setName(type);
				menuType.setItems(menuItems);

				menuTypes.add(menuType);
			}

			MenuCategory menuCategory = new MenuCategory();
			menuCategory.setName(category);
			menuCategory.setTypes(menuTypes);

			menuCategories.add(menuCategory);
		}

		menu.setCategories(menuCategories);

		return menu;
	}

	public Menu getMenuByID(final Long menuID) throws MenuNotFoundException
	{
		Menu menu = dao.getMenuByID(menuID);

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

}
