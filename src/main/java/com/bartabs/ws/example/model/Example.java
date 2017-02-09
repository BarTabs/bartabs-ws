package com.bartabs.ws.example.model;

public class Example {
	private String name;
	private String staffName;
	private Integer x;
	private Integer y;
	private Integer result;

	private Example shop;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Example getShop() {
		return shop;
	}

	public void setShop(Example shop) {
		this.shop = shop;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

}
