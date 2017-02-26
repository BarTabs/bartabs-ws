package com.bartabs.ws.menu.dataaccess.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bartabs.ws.menu.dataaccess.MenuDao;
import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.model.MenuItem;

@Repository("Menu.MenuDao")
public class MenuDaoImpl implements MenuDao
{
	@Autowired
	NamedParameterJdbcTemplate template;

	@Override
	public Menu getMenuByBarID(Long barID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Menu getMenuByID(Long menuID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long createMenu(Menu menu)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMenu(Menu menu)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeMenu(Long menuID)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<MenuItem> getMenuItems(Long menuID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MenuItem> getMenuItemsByCategory(Long menuID, String category)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MenuItem> getMenuItemsByType(Long menuID, String type)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long createMenuItem(MenuItem menuItem)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMenuItem(MenuItem menuItem)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeMenuItem(MenuItem menuItem)
	{
		// TODO Auto-generated method stub

	}

}