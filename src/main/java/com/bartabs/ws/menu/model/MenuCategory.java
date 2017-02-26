package com.bartabs.ws.menu.model;

import java.util.List;

public class MenuCategory
{
	private String name;
	private List<MenuType> types;
	private List<MenuItem> items;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<MenuType> getTypes()
	{
		return types;
	}

	public void setTypes(List<MenuType> types)
	{
		this.types = types;
	}

	public List<MenuItem> getItems()
	{
		return items;
	}

	public void setItems(List<MenuItem> items)
	{
		this.items = items;
	}

}
