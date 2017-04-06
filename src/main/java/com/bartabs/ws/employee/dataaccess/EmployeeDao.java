package com.bartabs.ws.employee.dataaccess;

import java.util.List;

import com.bartabs.ws.employee.model.Employee;

public interface EmployeeDao
{

	public List<Employee> getEmployeesByBarID(Long barID);

	public Employee getEmployeeByID(Long employeeID);

	public Long linkEmployeeToBar(Long barId, Long userID);

	public Employee getEmployeeByUserID(Long userID, Long barID);

}
