package com.order_service.id;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ID {
	
	@Id
	private int id;
	
	private int orderCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	
}
