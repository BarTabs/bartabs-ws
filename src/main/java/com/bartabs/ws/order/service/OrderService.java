package com.bartabs.ws.order.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bartabs.ws.Notification;
import com.bartabs.ws.NotificationService;
import com.bartabs.ws.exceptions.TokenDecodeException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.menu.model.MenuItem;
import com.bartabs.ws.order.dao.OrderDao;
import com.bartabs.ws.order.model.Order;
import com.bartabs.ws.user.model.User;
import com.bartabs.ws.user.service.UserService;

@Service("Order.OrderService")
@Transactional("txManager")
public class OrderService
{
	private final Log log = LogFactory.getLog(this.getClass());

	@Qualifier("Order.OrderDao")
	@Autowired
	private OrderDao dao;

	@Qualifier("User.UserService")
	@Autowired
	private UserService userService;

	@Qualifier("Notification.NotificationService")
	@Autowired
	private NotificationService notificationService;

	public void placeOrder(final Order order)
	{
		Long orderID = dao.placeOrder(order);

		List<MenuItem> orderItems = order.getOrderItems();
		if (orderItems != null && !orderItems.isEmpty()) {
			for (MenuItem oi : orderItems) {
				dao.linkOrderItem(orderID, oi);
			}
		}
	}

	public void placeOrderViaQrCode(Order order) throws UserNotFoundException
	{
		String uuid = order.getUuid();
		if (uuid != null && !uuid.isEmpty()) {
			User user = userService.getUserFromUuid(uuid);
			order.setOrderedBy(user.getObjectID());
			placeOrder(order);
		}

	}

	public List<Order> getOrdersByBarID(final Long barID, final Boolean openOnly)
	{
		List<Order> orders = dao.getBarOrders(barID, openOnly);
		return orders;
	}

	public List<MenuItem> getOrdersByUserID(Long userID)
	{
		List<MenuItem> menuItems = dao.getUserOrders(userID);
		return menuItems;
	}

	public Order getOrderByOrderID(final Long orderID)
	{
		Order order = dao.getOrder(orderID);
		return order;
	}

	public void completeOrder(Order order) throws UserNotFoundException, TokenDecodeException
	{
		dao.completeOrder(order);
		sendOrderCompleteNotification(order.getObjectID());
	}

	public List<MenuItem> getOrderItems(Long orderID)
	{
		return dao.getOrderItems(orderID);
	}

	public void sendOrderCompleteNotification(Long orderID) throws UserNotFoundException
	{
		Order order = getOrderByOrderID(orderID);
		User user = userService.getUserByID(order.getOrderedBy());
		String fcmToken = userService.retrieveFcmRegistrationToken(user.getObjectID());

		Notification notification = new Notification();
		notification.setTargetDeviceID(fcmToken);
		notification.setTitle("Order Status");
		StringBuilder body = new StringBuilder("Your order of:\n");

		for (MenuItem item : order.getOrderItems()) {
			body.append("-" + item.getName() + "\n");
		}

		body.append("has been completed");

		notification.setBody(body.toString());

		try {
			notificationService.sendNotification(notification);
		} catch (Exception ex) {
			log.error(ex.toString());
			return;
		}

	}

}
