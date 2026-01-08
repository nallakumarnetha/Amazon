package com.shared_contract.original;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shared_contract.original.order_service.OrderStatus;
import com.shared_contract.original.product_service.Category;

@JsonInclude(value = Include.NON_EMPTY)
public class FilterRequest {

	@JsonProperty("min_price")
	private double minPrice;
	
	@JsonProperty("max_price")
	private double maxPrice;
	
	@JsonProperty("min_count")
	private long minCount;
	
	@JsonProperty("max_count")
	private long maxCount;
	
	@JsonProperty("order_status")
	private OrderStatus orderStatus;
	
	private Category category;

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public long getMinCount() {
		return minCount;
	}

	public void setMinCount(long minCount) {
		this.minCount = minCount;
	}

	public long getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(long maxCount) {
		this.maxCount = maxCount;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
