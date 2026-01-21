package com.shared_contract.api.user_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shared_contract.dto.user_service.UserDTO;

public interface UserAPI {

	@GetMapping("users/{id}")
	UserDTO findUserById(@PathVariable("id") String id);

	@GetMapping("users/current-user")
	UserDTO getCurrentUser();
	
}
