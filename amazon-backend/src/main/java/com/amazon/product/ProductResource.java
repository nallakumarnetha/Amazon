package com.amazon.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.common.MyResponse;

import static com.amazon.common.MyLogger.log;

@RestController
@RequestMapping("/products")
public class ProductResource {

	@Autowired
	private ProductService service;
	
	@GetMapping
	public MyResponse findAllProducts() {
		log.info("request recieved: find all products");
		MyResponse response = service.findAllProducts();
		log.info("response sent: find all products");
		return response;
	}

	@GetMapping("/{id}")
	public Product findProductById(@PathVariable String id) {
		log.info("request recieved: find product by id");
		Product response = service.findProductById(id);
		log.info("response sent: find product by id");
		return response;
	}

	@PostMapping
	public Product addProduct(@RequestBody Product request) {
		log.info("request recieved: add product");
		Product response = service.addProduct(request);
		log.info("response sent: add product");
		return response;
	}
	
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
		log.info("request recieved: update product");
		Product response = service.updateProduct(id, product);
		log.info("response sent: update product");
		return response;
	}
	
	@DeleteMapping("{id}")
	public MyResponse removeProduct(@PathVariable String id) {
		log.info("request recieved: remove product");
		MyResponse response = service.removeProduct(id);
		log.info("response sent: remove product");
		return response;
	}

}
