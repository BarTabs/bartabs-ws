package com.bartabs.ws.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.example.dataaccess.ExampleDao;
import com.bartabs.ws.example.model.Example;

@Controller
public class ExampleController {
	@Autowired
	ExampleDao dao;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody Example getShopInJSON(@RequestParam(value = "name", required = false) String name) {
		return dao.getData();
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public @ResponseBody String examplePost(@RequestParam(value = "x", required = false) Integer x,
			@RequestParam(value = "y", required = false) Integer y) {
		String result = "Invalid format";

		if (x != null && y != null)
			result = "" + (x + y);

		return "<p>" + result + "</p>";
	}

}