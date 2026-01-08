package com.shared_contract.api.product_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shared_contract.dto.product_service.ProductDTO;

@RequestMapping("products")
public interface ProductAPI {

	@PostMapping
	ProductDTO addProduct(@RequestBody ProductDTO request);
	
	@GetMapping("/{id}")
	ProductDTO findProductById(@PathVariable String id);
}
