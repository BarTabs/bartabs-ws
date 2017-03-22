package com.bartabs.ws.employee.model;

import com.bartabs.ws.user.model.User;

public class Employee extends User {
	private Long userId;
	private Long employeeId;
	private Long barId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getBarId() {
		return barId;
	}

	public void setBarId(Long barId) {
		this.barId = barId;
	}

}