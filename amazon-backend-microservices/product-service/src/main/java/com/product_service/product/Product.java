package com.product_service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.amazon.common.Audit;
import com.amazon.common.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
@JsonInclude(value = Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@JsonProperty("product_id")
	private String productId;

	@Column(length = 1000)
	private String name;

	private double price;
	
	private List<String> files;
	
	// for sending to client in base64 format
	// Base64: Converts binary data into text format (letters, numbers, symbols)
	// Easy to send over JSON or APIs directly
	// preferred for in-line images
	@Transient
	@JsonProperty("base64_files")
	private Map<String,String> base64Files;

	private long count;
	
	@Transient
	@JsonProperty("cart_count")
	private long cartCount;	// CartProduct
	
	@Transient
	private Status status;	// CartProduct
	
	@Enumerated(EnumType.STRING)
	private com.amazon.product.Category category;
	
	@Embedded
	private Audit audit;

	public Product() {
		this.audit = new Audit();
	}

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

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
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

	public com.amazon.product.Category getCategory() {
		return category;
	}

	public void setCategory(com.amazon.product.Category category) {
		this.category = category;
	}
	
}
