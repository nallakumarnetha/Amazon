package com.user_service.user;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

	public User findByUserName(String userName);
	
	public User findByEmail(String email);
	
	public User findByGoogleUserId(String googleUserId);

}
