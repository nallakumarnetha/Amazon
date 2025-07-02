package com.amazon.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.common.MyResponse;

import jakarta.websocket.server.PathParam;

@RestController("/products")
public class ProductResource {

	@Autowired
	private ProductService service;
	
	@GetMapping
	public MyResponse findAllProducts() {
		MyResponse response = service.findAllProducts();
		return response;
	}

	@GetMapping("{id}")
	public Product findProductById(@PathParam("id") String id) {
		Product response = service.findProductById(id);
		return response;
	}

	@PostMapping
	public Product addProduct(Product request) {
		Product response = service.addProduct(request);
		return response;
	}
	
	@PutMapping("{id}")
	public Product updateProduct(@PathParam("id") String id, Product product) {
		Product response = service.updateProduct(id, product);
		return response;
	}
	
	@DeleteMapping("{id}")
	public MyResponse removeProduct(@PathParam("id") String id) {
		MyResponse response = service.removeProduct(id);
		return response;
	}

}
