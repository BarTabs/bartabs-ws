package com.bartabs.ws.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bartabs.ws.order.dao.OrderDao;
import com.bartabs.ws.order.model.Order;

@Service("Order.OrderService")
@Transactional("txManager")
public class OrderService
{
	@Qualifier("Order.OrderDao")
	@Autowired
	private OrderDao dao;

	public void placeOrder(final Order order)
	{
		dao.placeOrder(order);
	}

	public List<Order> getOrdersByBarID(final Long barID, final Boolean openOnly)
	{
		List<Order> orders = dao.getBarOrders(barID, openOnly);
		return orders;
	}

	public List<Order> getOrdersByUserID(Long userID)
	{
		List<Order> orders = dao.getUserOrders(userID);
		return orders;
	}

	public Order getOrderByOrderID(final Long orderID)
	{
		Order order = dao.getOrder(orderID);
		return order;
	}

}
