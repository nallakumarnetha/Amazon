package com.user_service.user;

import java.io.IOException;
import java.net.http.HttpResponse;

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
import org.springframework.web.bind.annotation.RestController;

import com.shared_contract.original.CustomException;
import com.shared_contract.original.user_service.AuthType;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("user/users")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public Response findAllUsers() {
		Response response = service.findAllUsers();
		return response;
	}

	@GetMapping("/{id}")
	public User findUserById(@PathVariable("id") String id) {
		User response = service.findUserById(id);
		return response;
	}

	@PostMapping
	public User addUser(@RequestBody User request, HttpServletResponse response) {
		User userResponse = service.addUser(request, response);
		return userResponse;
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable("id") String id, @RequestBody User request, HttpServletResponse response) throws CustomException {
		User user = service.updateUser(id, request, response);
		return user;
	}

	@DeleteMapping("/{id}")
	public Response removeUser(@PathVariable("id") String id) {
		Response response = service.removeUser(id);
		return response;
	}

	@GetMapping("current-user")
	public User getCurrentUser() {
		User response = service.getCurrentUser();
		return response;
	}
	
	@PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user, HttpServletResponse response) {
		ResponseEntity<User> responseUser = service.login(user, response);
		return responseUser;
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		ResponseEntity<User> responseUser = service.logout(response);
		return responseUser;
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User request, HttpServletResponse response) {
		request.setAuthType(AuthType.BASIC_TOKEN);
		User userResponse = service.addUser(request, response);
		return userResponse;
	}
	
	@GetMapping("/oauth2/callback")
	public void googleCallback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
		service.googleCallback(code, response);
		response.sendRedirect("http://localhost:4200/home");
	}
	
}
