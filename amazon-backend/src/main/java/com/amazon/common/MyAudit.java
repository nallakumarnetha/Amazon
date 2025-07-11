package com.amazon.common;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@Embeddable
public class MyAudit {

	@Column(columnDefinition = "timestamp")
	@CreatedDate
	private Instant createdDate;

	@Column(columnDefinition = "timestamp")
	@LastModifiedDate
	private Instant modifiedDate;

	@Column
	@CreatedBy
	private String createdBy;

	@Column
	@LastModifiedBy
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
