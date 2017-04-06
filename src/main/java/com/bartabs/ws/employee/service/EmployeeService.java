package com.bartabs.ws.employee.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bartabs.ws.employee.dataaccess.EmployeeDao;
import com.bartabs.ws.employee.model.Employee;
import com.bartabs.ws.exceptions.DuplicateUserNameException;
import com.bartabs.ws.exceptions.MissingUsernameException;
import com.bartabs.ws.exceptions.PasswordMissingException;
import com.bartabs.ws.exceptions.UserNotFoundException;
import com.bartabs.ws.user.service.UserService;

@Service("Employee.EmployeeService")
@Transactional("txManager")
public class EmployeeService
{
	@Qualifier("Employee.EmployeeDao")
	@Autowired
	private EmployeeDao dao;

	@Qualifier("User.UserService")
	@Autowired
	private UserService userService;

	public List<Employee> getEmployeesByBarID(Long barID)
	{
		return dao.getEmployeesByBarID(barID);
	}

	public Employee createEmployee(Employee employee) throws NoSuchAlgorithmException, InvalidKeySpecException,
			DuplicateUserNameException, PasswordMissingException, MissingUsernameException
	{
		// Create user from employee model
		Long userID = userService.createUser(employee);

		// Link user to a bar
		Long employeeID = dao.linkEmployeeToBar(employee.getBarId(), userID);

		// Fetch the new employee (which is really a user)
		Employee newEmployee = dao.getEmployeeByID(employeeID);

		return newEmployee;
	}

	public Employee updateEmployee(Employee employee) throws UserNotFoundException
	{

		employee.setObjectID(employee.getUserId());
		userService.updateUser(employee);
		final Employee updatedEmployee = dao.getEmployeeByID(employee.getEmployeeId());

		return updatedEmployee;

	}

	public void removeEmployee(Long employeeID) throws UserNotFoundException
	{
		Employee employee = getEmployeeByID(employeeID);
		userService.removeUser(employee);
	}

	public Employee getEmployeeByUserID(Long userID, Long barID)
	{
		return dao.getEmployeeByUserID(userID, barID);
	}

	public Employee getEmployeeByID(Long employeeID)
	{
		return dao.getEmployeeByID(employeeID);
	}

}