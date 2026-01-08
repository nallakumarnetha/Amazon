package com.order_service.common;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Audit {

	@Column(columnDefinition = "timestamp")
	@CreatedDate
	@JsonProperty("created_date")
	private Instant createdDate;

	@Column(columnDefinition = "timestamp")
	@LastModifiedDate
	@JsonProperty("modified_date")
	private Instant modifiedDate;

	@Column
	@CreatedBy
	@JsonProperty("created_by")
	private String createdBy;

	@Column
	@LastModifiedBy
	@JsonProperty("modified_by")
	private String modifiedBy;

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
