package com.bartabs.ws.menu.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MenuItem
{
	private Long objectID;
	private Long menuID;
	private String name;
	private String description;
	private BigDecimal price;
	private Date createdDate;
	private Date modifiedDate;
	private String category;
	private String type;
	private List<MenuItemIngredient> ingredients;

	public Long getObjectID()
	{
		return objectID;
	}

	public void setObjectID(Long objectID)
	{
		this.objectID = objectID;
	}

	public Long getMenuID()
	{
		return menuID;
	}

	public void setMenuID(Long menuID)
	{
		this.menuID = menuID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public Date getModifiedDate()
	{
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate)
	{
		this.modifiedDate = modifiedDate;
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

	public List<MenuItemIngredient> getIngredients()
	{
		return ingredients;
	}

	public void setIngredients(List<MenuItemIngredient> ingredients)
	{
		this.ingredients = ingredients;
	}

}
