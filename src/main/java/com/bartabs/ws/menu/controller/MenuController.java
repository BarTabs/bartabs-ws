package com.bartabs.ws.menu.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.Response;
import com.bartabs.ws.exceptions.MenuNotFoundException;
import com.bartabs.ws.menu.criteria.MenuCriteria;
import com.bartabs.ws.menu.model.Menu;
import com.bartabs.ws.menu.service.MenuService;

@Controller("Menu.MenuController")
public class MenuController extends Response
{

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier("Menu.MenuService")
	private MenuService service;

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
			log.error(ex.toString(), ex);

			return buildErrorResponse(ex.getMessage());
		}

	}

	@RequestMapping(value = "/menu/createmenu", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response createMenu(final Menu menu)
	{
		try {
			Long menuID = service.createMenu(menu);
			Menu newMenu = service.getMenuByID(menuID);

			return buildResponse(newMenu);
		} catch (MenuNotFoundException ex) {
			return buildErrorResponse(ex.getMessage());
		} catch (Exception ex) {
			return buildErrorResponse("Error processing menu creation");
		}
	}

	@RequestMapping(value = "/menu/updatemenu", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response updateMenu(final Menu menu)
	{
		try {
			Menu updatedMenu;
			updatedMenu = service.updateMenu(menu);

			return buildResponse(updatedMenu);
		} catch (MenuNotFoundException e) {

			return buildErrorResponse(e.getMessage());
		}

	}

	@RequestMapping(value = "/menu/deletemenu", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response deleteMenu(@RequestParam("menuID") final Long menuID)
	{
		service.removeMenu(menuID);

		return buildResponse("Ok");
	}
}
