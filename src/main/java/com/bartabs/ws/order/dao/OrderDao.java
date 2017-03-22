package com.bartabs.ws.order.dao;

import java.util.List;

import com.bartabs.ws.menu.model.MenuItem;
import com.bartabs.ws.order.model.Order;

public interface OrderDao
{
	public void placeOrder(Order order);

	public List<Order> getBarOrders(Long barID, Boolean openOnly);

	public List<Order> getUserOrders(Long userID);

	public Order getOrder(Long orderID);

	public List<MenuItem> getOrderItems(Long orderID);

}