/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.menu.dataaccess;

import java.util.List;

import com.bartabs.ws.menu.criteria.MenuCriteria;
import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.model.MenuItem;

public interface MenuDao
{

	public Menu getMenuByBarID(Long barID);

	public Menu getMenuByID(Long menuID);

	public Long createMenu(Menu menu);

	public void updateMenu(Menu menu);

	public void removeMenu(Long menuID);

	public List<String> getCategories(MenuCriteria criteria);

	public List<String> getTypes(MenuCriteria criteria);

	public MenuItem getMenuItemsByID(Long objectID);

	public List<MenuItem> getMenuItems(MenuCriteria criteria);

	public List<MenuItem> getMenuItemsByMenuID(Long menuID);

	public List<MenuItem> getMenuItemsByCategory(Long menuID, String category);

	public List<MenuItem> getMenuItemsByType(Long menuID, String type);

	public Long createMenuItem(MenuItem menuItem);

	public void updateMenuItem(MenuItem menuItem);

	public void removeMenuItem(Long menuItemID);

}
