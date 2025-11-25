package com.amazon.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	public User findByUserName(String userName);
	
	public User findByGoogleUserId(String googleUserId);

}
