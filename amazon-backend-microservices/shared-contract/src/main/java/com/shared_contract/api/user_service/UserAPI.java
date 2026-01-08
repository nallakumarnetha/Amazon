package com.shared_contract.api.user_service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.user_service.user.AuthType;
import com.user_service.user.CustomException;
import com.user_service.user.Response;
import com.user_service.user.User;
import com.user_service.user.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("users")
public interface UserAPI {

	@GetMapping
	ResponseDTO findAllUsers();

	@GetMapping("/{id}")
	UserDTO findUserById(@PathVariable String id);

	@PostMapping
	UserDTO addUser(@RequestBody User request, HttpServletResponse response);

	@PutMapping("/{id}")
	UserDTO updateUser(@PathVariable String id, @RequestBody User request, HttpServletResponse response);

	@DeleteMapping("/{id}")
	ResponseDTO removeUser(@PathVariable String id);

	@GetMapping("current-user")
	UserDTO getCurrentUser();
	
	@PostMapping("login")
    ResponseEntity<UserDTO> login(@RequestBody User user, HttpServletResponse response);
	
	@PostMapping("logout")
	ResponseEntity<?> logout(HttpServletResponse response);
	
	@PostMapping("register")
	UserDTO registerUser(@RequestBody User request, HttpServletResponse response);
	
	@GetMapping("oauth2/callback")
	void googleCallback(@RequestParam String code, HttpServletResponse response);
	
}
