package com.amazon.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomException extends Exception {
	
	@JsonProperty("status code")
	private int statusCode;
	
	private String message;
	
	private String details;

	public CustomException() {
		
	}
	
	public CustomException(String message) {
		super();
		this.message = message;
	}
	
	public CustomException(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}
