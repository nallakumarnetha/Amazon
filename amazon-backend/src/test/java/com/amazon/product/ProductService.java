package com.amazon.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.common.MyResponse;

import jakarta.websocket.server.PathParam;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public MyResponse findAllProducts() {
		List<Product> products = repository.findAll();
		MyResponse response = new MyResponse();
		response.setProducts(products);
		return response;
	}

	public Product findProductById(@PathParam("id") String id) {
		Product response = repository.findById(id).orElse(null);
		return response;
	}

	public Product addProduct(Product request) {
		Product response = repository.save(request);
		return response;
	}
	
	public Product updateProduct(@PathParam("id") String id, Product product) {
		Product response = repository.save(product);
		return response;
	}
	
	public MyResponse removeProduct(@PathParam("id") String id) {
		repository.deleteById(id);
		MyResponse response = new MyResponse();
		return response;
	}
	
}
