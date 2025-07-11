package com.amazon.product;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.amazon.common.MyAudit;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonInclude(value = Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column
	private String name;

	@Column
	private double price;

	@Embedded
	private MyAudit audit;

	public Product() {
		this.audit = new MyAudit();
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

	public MyAudit getAudit() {
		return audit;
	}

	public void setAudit(MyAudit audit) {
		this.audit = audit;
	}

}
