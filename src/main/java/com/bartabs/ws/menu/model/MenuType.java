package com.bartabs.ws.menu.model;

import java.util.List;

public class MenuType
{
	private String name;
	private List<MenuItem> items;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
