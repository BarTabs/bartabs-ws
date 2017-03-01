package com.bartabs.ws.menu.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Menu
{
	private Long objectID;
	private Long barID;
	private Date createdDate;
	private Date modifiedDate;

	private List<MenuItem> items;

	public Long getObjectID()
	{
		return objectID;
	}

	public void setObjectID(Long objectID)
	{
		this.objectID = objectID;
	}

	public Long getBarID()
	{
		return barID;
	}

	public void setBarID(Long barID)
	{
		this.barID = barID;
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

	public List<MenuItem> getItems()
	{
		return items;
	}

	public void setItems(List<MenuItem> items)
	{
		this.items = items;
	}

}
