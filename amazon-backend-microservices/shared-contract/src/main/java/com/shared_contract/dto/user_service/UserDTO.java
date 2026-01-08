package com.shared_contract.dto.user_service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user_service.user.Address;
import com.user_service.user.Audit;
import com.user_service.user.XAuthType;
import com.user_service.user.XGender;
import com.user_service.user.XLanguage;
import com.user_service.user.XRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

public class UserDTO {

	private String id;
	
	@JsonProperty("user_id")
	private String userId;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	private String name;	// full name = firstName + lastName

	@JsonProperty("phone_number")
	private String phoneNumber;

	private Gender gender;

	private Address address;

	private XRole role;
	
	private XLanguage language;
	
	private List<String> files;
	
	@JsonProperty("base64_files")
	private Map<String,String> base64Files;
	
	private String email;
	
	private Date dob;	
	
	// auth start
	
	@JsonProperty("auth_type")
	private XAuthType authType;
	
	@JsonProperty("user_name")
	private String userName;
	
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
	
	@JsonProperty("access_token")
	private String accessToken;	// JWT = JSON Web Token
	
	@JsonProperty("google_user_id")
	private String googleUserId;
	
	@JsonProperty("google_refresh_token")
	private String googleRefreshToken;
	
	@JsonProperty("microsoft_user_id")
	private String microsoftUserId;		// for future purpose
	
	// auth end

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public List<String> getFiles() {
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

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getGoogleUserId() {
		return googleUserId;
	}

	public void setGoogleUserId(String googleUserId) {
		this.googleUserId = googleUserId;
	}
	
	public String getGoogleRefreshToken() {
		return googleRefreshToken;
	}

	public void setGoogleRefreshToken(String googleRefreshToken) {
		this.googleRefreshToken = googleRefreshToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getMicrosoftUserId() {
		return microsoftUserId;
	}

	public void setMicrosoftUserId(String microsoftUserId) {
		this.microsoftUserId = microsoftUserId;
	}

}
