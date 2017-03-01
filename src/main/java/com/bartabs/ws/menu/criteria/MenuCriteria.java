package com.bartabs.ws.menu.criteria;

public class MenuCriteria
{
	private Long barID;
	private Long menuID;
	private String category;
	private String type;

	public Long getBarID()
	{
		return barID;
	}

	public void setBarID(Long barID)
	{
		this.barID = barID;
	}

	public Long getMenuID()
	{
		return menuID;
	}

	public void setMenuID(Long menuID)
	{
		this.menuID = menuID;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}
