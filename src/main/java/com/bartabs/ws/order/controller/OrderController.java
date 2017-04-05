package com.bartabs.ws.order.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.Response;
import com.bartabs.ws.menu.model.MenuItem;
import com.bartabs.ws.order.model.Order;
import com.bartabs.ws.order.service.OrderService;

@Controller("Order.OrderController")
public class OrderController extends Response
{
	private final Log log = LogFactory.getLog(this.getClass());

	@Qualifier("Order.OrderService")
	@Autowired
	private OrderService service;

	@RequestMapping(value = "/order/getbarorders", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getOrders(@RequestParam("barID") Long barID,
			@RequestParam(value = "openOnly", required = false) Boolean openOnly)
	{
		try {
			List<Order> orders = service.getOrdersByBarID(barID, openOnly);
			return buildResponse(orders);
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error getting open orders.");
		}
	}

	@RequestMapping(value = "/order/getuserorders", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getUserOrders(@RequestParam("userID") Long barID)
	{
		try {
			List<MenuItem> menuItems = service.getOrdersByUserID(barID);
			return buildResponse(menuItems);
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error getting user orders.");
		}
	}

	@RequestMapping(value = "/order/getorder", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getMenu(@RequestParam("orderID") Long orderID)
	{

		try {

			final Order order = service.getOrderByOrderID(orderID);
			return buildResponse(order);

		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error retrieving order");
		}

	}

	@RequestMapping(value = "/order/placeorder", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response updateMenuItem(@RequestBody final Order order)
	{
		try {
			service.placeOrder(order);
			return buildResponse("Thank you. Your order is being processed");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error processing order");
		}
	}

	@RequestMapping(value = "/order/completeorder", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response completeOrder(@RequestBody final Order order)
	{
		try {
			service.completeOrder(order);

			return buildResponse("Order complete");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error marking order as complete");
		}
	}

}
