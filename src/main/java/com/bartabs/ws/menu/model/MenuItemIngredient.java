package com.bartabs.ws.menu.model;

public class MenuItemIngredient
{
	private Long objectID;
	private Long menuItemID;
	private Long inventoryItemID;
	private Integer quantity;
	private String comment;

	public Long getObjectID()
	{
		return objectID;
	}

	public void setObjectID(Long objectID)
	{
		this.objectID = objectID;
	}

	public Long getMenuItemID()
	{
		return menuItemID;
	}

	public void setMenuItemID(Long menuItemID)
	{
		this.menuItemID = menuItemID;
	}

	public Long getInventoryItemID()
	{
		return inventoryItemID;
	}

	public void setInventoryItemID(Long inventoryItemID)
	{
		this.inventoryItemID = inventoryItemID;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

}
