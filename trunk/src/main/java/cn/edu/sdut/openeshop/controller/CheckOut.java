package cn.edu.sdut.openeshop.controller;

import cn.edu.sdut.openeshop.model.Address;

public interface CheckOut {

	public void wire();
	public Address getAddress();
	public void setAddress(Address address);
	public String createOrder();
	public String submitOrder();
}
