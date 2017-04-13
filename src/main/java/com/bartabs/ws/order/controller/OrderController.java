package com.bartabs.ws.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.bartabs.ws.employee.model.Employee;
import com.bartabs.ws.employee.service.EmployeeService;
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

	@Qualifier("Employee.EmployeeService")
	@Autowired
	private EmployeeService employeeService;

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
	public @ResponseBody Response getOrder(@RequestParam("orderID") Long orderID)
	{

		try {

			final Order order = service.getOrderByOrderID(orderID);
			return buildResponse(order);

		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error retrieving order");
		}

	}

	@RequestMapping(value = "/order/getorderitems", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getOrderItem(@RequestParam("orderID") Long orderID)
	{

		try {

			final List<MenuItem> orderItems = service.getOrderItems(orderID);
			return buildResponse(orderItems);

		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error retrieving order");
		}

	}

	@RequestMapping(value = "/order/placeorder", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response placeOrder(@RequestBody final Order order)
	{
		try {
			service.placeOrder(order);
			return buildResponse("Thank you. Your order is being processed");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error processing order");
		}
	}

	@RequestMapping(value = "/order/placeorderviaqrcode", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response placeOrderViaQRCode(@RequestBody final Order order)
	{
		try {
			service.placeOrderViaQrCode(order);
			return buildResponse("Thank you. Your order is being processed");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error processing order");
		}
	}

	@RequestMapping(value = "/order/completeorder", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response completeOrder(HttpServletRequest request, @RequestBody final Order order)
	{
		try {
			Employee employee = employeeService.getEmployeeByUserID(order.getCompletedBy(), order.getBarID());
			order.setCompletedBy(employee.getEmployeeId());
			service.completeOrder(order);

			return buildResponse("Order complete");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error marking order as complete");
		}
	}

}
