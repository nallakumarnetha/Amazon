package com.common_service.common;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shared_contract.dto.order_service.OrderDTO;
import com.shared_contract.dto.product_service.ProductDTO;
import com.shared_contract.dto.user_service.AddressDTO;
import com.shared_contract.dto.user_service.UserDTO;

@JsonInclude(value = Include.NON_EMPTY)
public class Response {

	int start = 0;

	int rows = 10;

	long total;

	private String message;

	private List<ProductDTO> products;

	private List<UserDTO> users;
	
	private List<OrderDTO> orders;
	
	private List<AddressDTO> addresses;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public List<AddressDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressDTO> addresses) {
		this.addresses = addresses;
	}
	
}
