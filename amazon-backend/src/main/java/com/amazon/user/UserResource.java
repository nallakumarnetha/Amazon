package com.amazon.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.common.Response;
import com.amazon.product.Product;
import com.amazon.product.ProductService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public Response findAllUsers() {
		Response response = service.findAllUsers();
		return response;
	}

	@GetMapping("/{id}")
	public User findUserById(@PathVariable String id) {
		User response = service.findUserById(id);
		return response;
	}

	@PostMapping
	public User addUser(@RequestBody User request) {
		User response = service.addUser(request);
		return response;
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable String id, @RequestBody User request) {
		User response = service.updateUser(id, request);
		return response;
	}

	@DeleteMapping("/{id}")
	public Response removeUser(@PathVariable String id) {
		Response response = service.removeUser(id);
		return response;
	}

	@GetMapping("current-user")
	public User getCurrentUser() {
		User response = service.getCurrentUser();
		return response;
	}
}
