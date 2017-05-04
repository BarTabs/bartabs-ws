package com.bartabs.ws.bar.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.Response;
import com.bartabs.ws.bar.criteria.BarCriteria;
import com.bartabs.ws.bar.model.Bar;
import com.bartabs.ws.bar.service.BarService;

@Controller("Bar.BarController")
public class BarController extends Response {
	private final Log log = LogFactory.getLog(this.getClass());

	@Qualifier("Bar.BarService")
	@Autowired
	private BarService service;

	@RequestMapping(value = "/bar/getbars", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getBars(BarCriteria criteria) {

		try {

			final List<Bar> bars = service.getBars(criteria);
			return buildResponse(bars);

		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error retrieving bars");
		}

	}

	@RequestMapping(value = "/bar/createbar", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response authenticate(@RequestBody Bar bar) {

		try {
			Long objectID = service.createBar(bar);
			Bar newBar = service.getBar(objectID);

			return buildResponse(newBar);
		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error creating bar");
		}

	}
	
	@RequestMapping(value = "/bar/deletebar", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response deleteBar(BarCriteria criteria) {

		try {

			service.deleteBar(criteria.getBarID());
			return buildResponse("Bar successfully removed.");

		} catch (Exception ex) {
			log.error(ex.toString(), ex);
			return buildErrorResponse("Error removing bar");
		}

	}
}
