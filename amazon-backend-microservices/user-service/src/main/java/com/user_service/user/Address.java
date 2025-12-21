package com.amazon.user;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.amazon.common.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonInclude(value = Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	private String street;

	private String city;

	private String pincode;

	@Embedded
	private Audit audit;
	
	public Address() {
		this.audit = new Audit();
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	public Audit getAudit() {
		return audit;
	}
	
	public void setAudit(Audit audit) {
		this.audit = audit;
	}

}
