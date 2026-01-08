package com.shared_contract.dto.common_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shared_contract.original.Color;

public class PreferencesDTO {

	private String id;
	
	@JsonProperty("user_id")
	private String userId;
	
	private boolean ai = true;

	private Color color = Color.Blue;
	
	@JsonProperty("text_value")
	private String textValue;
	
	@JsonProperty("hex_color")
	private String hexColor;
	
	private boolean prime = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isAi() {
		return ai;
	}

	public void setAi(boolean ai) {
		this.ai = ai;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public boolean isPrime() {
		return prime;
	}

	public void setPrime(boolean prime) {
		this.prime = prime;
	}
	
}
