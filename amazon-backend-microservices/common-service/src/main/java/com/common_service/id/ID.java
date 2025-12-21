package com.amazon.id;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ID {
	
	@Id
	private int id;
	
	private int productCount;
	
	private int userCount;
	
	private int orderCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	
}
