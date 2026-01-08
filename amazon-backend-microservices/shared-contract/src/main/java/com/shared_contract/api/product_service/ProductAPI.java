package com.shared_contract.api.product_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.product_service.product.FilterRequest;
import com.product_service.product.Product;
import com.product_service.product.ProductService;
import com.product_service.product.Response;

@RequestMapping("products")
public interface ProductAPI {

	// CRUD start

	@GetMapping
	ResponseDTO findAllProducts(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size);

	@GetMapping("/{id}")
	ProductDTO findProductById(@PathVariable String id);

	@PostMapping
	ProductDTO addProduct(@RequestBody Product request);

	@PutMapping("/{id}")
	ProductDTO updateProduct(@PathVariable String id, @RequestBody Product product);

	@DeleteMapping("{id}")
	ResponseDTO deleteProduct(@PathVariable String id);
	// CRUD end

	@GetMapping("/search")
	ResponseDTO searchProducts(@RequestParam String name);
	
	@PostMapping("/filter")
	ResponseDTO filterProducts(@RequestBody FilterRequest request);
	
}
