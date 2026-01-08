package com.shared_contract.dto.product_service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shared_contract.original.Status;
import com.shared_contract.original.product_service.Category;

public class ProductDTO {

	private String id;
	
	@JsonProperty("product_id")
	private String productId;

	private String name;

	private double price;
	
	private List<String> files;
	
	// for sending to client in base64 format
	// Base64: Converts binary data into text format (letters, numbers, symbols)
	// Easy to send over JSON or APIs directly
	// preferred for in-line images
	@JsonProperty("base64_files")
	private Map<String,String> base64Files;

	private long count;
	
	@JsonProperty("cart_count")
	private long cartCount;	// CartProduct
	
	private Status status;	// CartProduct
	
	private Category category;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<String> getFiles() {
		if(this.files == null) {
			return new ArrayList<String>();
		}
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public Map<String, String> getBase64Files() {
		return base64Files;
	}

	public void setBase64Files(Map<String, String> base64Files) {
		this.base64Files = base64Files;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getCartCount() {
		return cartCount;
	}

	public void setCartCount(long cartCount) {
		this.cartCount = cartCount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
