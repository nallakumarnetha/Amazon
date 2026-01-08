package com.shared_contract.dto.order_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shared_contract.original.order_service.OrderStatus;
import com.shared_contract.original.product_service.Category;

public class OrderDTO {

	private String id;
	
	@JsonProperty("order_id")
	private String orderId;
	
	@JsonProperty("user_id")
	private String userId;
	
	@JsonProperty("product_id")
	private String productId;
	
	private int count;
	
	private double amount;
	
	@JsonProperty("payment_id")
	private String paymentId;
	
	private OrderStatus status;
	
	private Category category;
	
	private String address;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
