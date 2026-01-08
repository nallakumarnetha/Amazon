package com.shared_contract.api.user_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shared_contract.dto.user_service.UserDTO;

@RequestMapping("users")
public interface UserAPI {

	@GetMapping("/{id}")
	UserDTO findUserById(@PathVariable String id);

	@GetMapping("current-user")
	UserDTO getCurrentUser();
	
}
