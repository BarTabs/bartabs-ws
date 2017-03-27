package com.bartabs.ws.employee.controller;

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
import com.bartabs.ws.employee.model.Employee;
import com.bartabs.ws.employee.service.EmployeeService;

@Controller("Employee.EmployeeController")
public class EmployeeController extends Response
{

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier("Employee.EmployeeService")
	private EmployeeService service;

	@RequestMapping(value = "/employee/getemployees", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response getEmployees(@RequestParam(value = "barID", required = false) Long barID)
	{

		try {
			if (barID != null) {
				List<Employee> employees = service.getEmployeesByBarID(barID);

				return buildResponse(employees);
			} else {
				return buildErrorResponse("Bar ID not specified in the parameters.");
			}

		} catch (Exception ex) {
			log.error(ex.toString(), ex);

			return buildErrorResponse(ex.getMessage());
		}

	}

	@RequestMapping(value = "/employee/createemployee", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response createEmployee(@RequestBody final Employee employee)
	{
		try {
			Employee newEmployee = service.createEmployee(employee);

			return buildResponse(newEmployee);
		} catch (Exception ex) {
			return buildErrorResponse(ex.getMessage());
		}
	}

	@RequestMapping(value = "/employee/updateemployee", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response updateEmployee(@RequestBody final Employee employee)
	{
		try {
			Long userID = employee.getUserId();
			employee.setObjectID(userID);

			Employee updatedEmployee = service.updateEmployee(employee);
			return buildResponse(updatedEmployee);

		} catch (Exception e) {

			return buildErrorResponse(e.getMessage());
		}
	}

	@RequestMapping(value = "/employee/deleteemployee", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response deleteEmployee(@RequestBody final Employee employee)
	{
		try {
			Long employeeID = employee.getEmployeeId();
			service.removeEmployee(employeeID);
			return buildResponse("Employee deleted successfully");

		} catch (Exception e) {

			return buildErrorResponse(e.getMessage());
		}
	}
}
