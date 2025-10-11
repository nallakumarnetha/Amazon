package com.amazon.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.common.Response;
import com.amazon.product.Product;
import com.amazon.product.ProductRepository;

import jakarta.websocket.server.PathParam;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public Response findAllUsers() {
		List<User> users = repository.findAll();
		Response response = new Response();
		response.setUsers(users);
		return response;
	}

	public User findUserById(String id) {
		User response = repository.findById(id).orElse(null);
		return response;
	}

	public User addUser(User request) {
		User response = repository.save(request);
		return response;
	}

	public User updateUser(String id, User request) {
		User response = repository.save(request);
		return response;
	}

	public Response removeUser(String id) {
		repository.deleteById(id);
		Response response = new Response();
		return response;
	}

}
