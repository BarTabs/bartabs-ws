package com.bartabs.ws.order.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.bartabs.ws.menu.model.MenuItem;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends OrderInfo
{
	private Long objectID;
	private String uuid;
	private List<MenuItem> orderItems;

	public Long getObjectID()
	{
		return objectID;
	}

	public void setObjectID(Long objectID)
	{
		this.objectID = objectID;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public List<MenuItem> getOrderItems()
	{
		return orderItems;
	}

	public void setOrderItems(List<MenuItem> orderItems)
	{
		this.orderItems = orderItems;
	}

}
