package com.bartabs.ws.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bartabs.ws.Response;
import com.bartabs.ws.example.dataaccess.ExampleDao;

@Controller
public class ExampleController extends Response
{
	@Autowired
	ExampleDao dao;

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getShopInJSON(@RequestParam(value = "name", required = false) String name)
	{
		return buildResponse(dao.getData());
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response examplePost(@RequestParam(value = "x", required = false) Integer x,
			@RequestParam(value = "y", required = false) Integer y)
	{
		String result = "Invalid format";

		if (x != null && y != null)
			result = "" + (x + y);

		return buildResponse(result);
	}

}