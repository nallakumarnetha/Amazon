package com.product_service.id;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ID {
	
	@Id
	private int id;
	
	private int productCount;
	
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

}
