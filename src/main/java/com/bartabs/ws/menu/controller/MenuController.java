/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.menu.controller;

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
import com.bartabs.ws.exceptions.MenuNotFoundException;
import com.bartabs.ws.menu.criteria.MenuCriteria;
import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.model.MenuItem;
import com.bartabs.ws.menu.service.MenuService;

/**
 * The {@code MenuController} class defines the API routes available to the
 * users for placing, completing and viewing orders
 * 
 * @author Victor Lora
 * @version 1.0
 * @see com.bartabs.ws.menu.service.MenuService
 * @since 2014-04-12
 *
 */
@Controller("Menu.MenuController")
public class MenuController extends Response
{

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier("Menu.MenuService")
	private MenuService service;

	/**
	 * Route by which a user retrieves a bars menu from the database
	 * 
	 * @param criteria
	 *            a set of criteria specifying the type of menu the user is
	 *            requesting.
	 *            <p>
	 *            the barID field is required for the criteria
	 *            <p>
	 *            when nothing but the barID is specified, all the available
	 *            menu categories are retrieved and returned in the response
	 *            <p>
	 *            if a category is specified, then all types under that category
	 *            are retrieved and returned in the response
	 *            <p>
	 *            if a category and type is specified, then all the menu items
	 *            associated with those two criteria are retrieved and returned
	 *            <p>
	 *            it is also possible to retrieve a menu by specifying the
	 *            menuID field in the {@code MenuCriteria}
	 * @return a {@code Response} containing the requested level of information
	 */
	@RequestMapping(value = "/menu/getmenu", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getMenu(final MenuCriteria criteria)
	{

		try {
			log.debug("Getting menu for bar: " + criteria.getBarID());

			Response response = new Response();
			if (criteria.getType() != null) {
				response = buildResponse(service.getMenu(criteria));
			} else if (criteria.getCategory() != null) {
				response = buildResponse(service.getTypes(criteria));
			} else if (criteria.getBarID() != null) {
				response = buildResponse(service.getCategories(criteria));
			} else if (criteria.getMenuID() != null) {
				response = buildResponse(service.getMenuByID(criteria.getMenuID()));
			} else {
				response = buildErrorResponse("Illegal parameters.");
			}

			return response;

		} catch (MenuNotFoundException ex) {
			log.error(ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error retrieving menu");
		}

	}

	/**
	 * Route by which a user defines a new menu
	 * 
	 * @param menu
	 *            a {@code Menu} object
	 * @return a {@code Response} containing the newly defined menu
	 */
	@RequestMapping(value = "/menu/createmenu", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response createMenu(@RequestBody final Menu menu)
	{
		try {
			Long menuID = service.createMenu(menu);
			Menu newMenu = service.getMenuByID(menuID);

			return buildResponse(newMenu);
		} catch (MenuNotFoundException ex) {
			log.error(ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error processing menu creation");
		}
	}

	/**
	 * Route by which a menu is updated
	 * 
	 * @param menu
	 *            a {@code Menu} object
	 * @return a {@code Response} with the updated menu
	 */
	@RequestMapping(value = "/menu/updatemenu", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response updateMenu(@RequestBody final Menu menu)
	{
		try {
			Menu updatedMenu;
			updatedMenu = service.updateMenu(menu);

			return buildResponse(updatedMenu);
		} catch (MenuNotFoundException ex) {
			log.error(ex.getMessage(), ex);
			return buildErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse(ex.getMessage());
		}

	}

	/**
	 * Route by which a menu is deleted
	 * 
	 * @param menu
	 *            a {@code Menu} object
	 * @return a {@code Response} with a message "Ok"
	 */
	@RequestMapping(value = "/menu/deletemenu", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response deleteMenu(@RequestBody final Menu menu)
	{
		try {
			Long menuID = menu.getObjectID();
			service.removeMenu(menuID);

			return buildResponse("Ok");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);

			return buildErrorResponse("Error deleting menu");
		}
	}

	/**
	 * Route by which a list of a bars categories are retrieved
	 * 
	 * @param criteria
	 *            must include a barID
	 * @return a {@code Response} with the requested categories (if found)
	 */
	@RequestMapping(value = "/menu/getcategories", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response get(final MenuCriteria criteria)
	{
		try {
			List<String> categories = service.getCategories(criteria);
			return buildResponse(categories);
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error getting categories.");
		}
	}

	/**
	 * Route by which a user retrieves a specific menu item from the database
	 * 
	 * @param objectID
	 *            a {@code MenuItem}'s items primary key
	 * @return a {@code Response} containing the requested menu item
	 */
	@RequestMapping(value = "/menu/getmenuitem", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getMenu(@RequestParam("objectID") Long objectID)
	{

		try {

			final MenuItem menuItem = service.getMenuItemByID(objectID);

			return buildResponse(menuItem);

		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse(ex.getMessage());
		}

	}

	/**
	 * Route by which a menu item gets created
	 * 
	 * @param menuItem
	 *            a {@code MenuItem} with corresponding fields
	 * @return a {@code Response} with the new menu item
	 */
	@RequestMapping(value = "/menu/createmenuitem", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response createMenuItem(@RequestBody final MenuItem menuItem)
	{
		try {
			MenuItem newMenuItem = service.createMenuItem(menuItem);

			return buildResponse(newMenuItem);
		} catch (Exception ex) {
			log.error(ex.toString(), ex);

			return buildErrorResponse("Error creating menu item.");
		}
	}

	/**
	 * Route by which a user can update a menu item
	 * 
	 * @param menuItem
	 *            a {@code MenuItem} with updated fields
	 * @return a {@code Response} with containing the updated menu item
	 */
	@RequestMapping(value = "/menu/updatemenuitem", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response updateMenuItem(@RequestBody final MenuItem menuItem)
	{
		try {
			MenuItem updatedMenuItem = service.updateMenuItem(menuItem);

			return buildResponse(updatedMenuItem);
		} catch (Exception ex) {
			log.error(ex.toString(), ex);

			return buildErrorResponse("Error updating menu item.");
		}
	}

	/**
	 * Route by which a menu item is removed from the menu
	 * 
	 * @param menuItem
	 *            a {@code MenuItem} with the given primary key for the item to
	 *            be removed
	 * @return a {@code Response} with a message of "Ok"
	 */
	@RequestMapping(value = "/menu/deletemenuitem", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response deleteMenuItem(@RequestBody final MenuItem menuItem)
	{
		try {
			Long objectID = menuItem.getObjectID();
			service.removeMenuItem(objectID);
			return buildResponse("Ok");
		} catch (Exception ex) {
			log.error(ex.toString(), ex);

			return buildErrorResponse("Error deleting menu item.");
		}
	}

}
