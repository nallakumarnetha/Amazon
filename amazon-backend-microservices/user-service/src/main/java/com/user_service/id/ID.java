package com.user_service.id;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ID {
	
	@Id
	private int id;
	
	private int userCount;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

}
