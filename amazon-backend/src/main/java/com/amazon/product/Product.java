package com.amazon.product;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.amazon.common.Audit;
import com.amazon.id.ID;
import com.amazon.id.IdService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysql.cj.protocol.ColumnDefinition;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
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

	private String name;

	private double price;
	
	private List<String> files;
	
	@Transient
	@JsonProperty("base64_files")
	private Map<String,String> base64Files;

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
	
}
