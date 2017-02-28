package com.bartabs.ws.menu.dataaccess;

import java.util.List;

import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.model.MenuItem;

public interface MenuDao
{

	public Menu getMenuByBarID(Long barID);

	public Menu getMenuByID(Long menuID);

	public Long createMenu(Menu menu);

	public void updateMenu(Menu menu);

	public void removeMenu(Long menuID);

	public List<String> getCategories(Long menuID);

	public List<String> getTypes(Long menuID, String category);

	List<MenuItem> getMenuItems(Long menuID, String category, String type);

	public List<MenuItem> getMenuItems(Long menuID);

	public List<MenuItem> getMenuItemsByCategory(Long menuID, String category);

	public List<MenuItem> getMenuItemsByType(Long menuID, String type);

	public Long createMenuItem(MenuItem menuItem);

	public void updateMenuItem(MenuItem menuItem);

	public void removeMenuItem(MenuItem menuItem);

}
