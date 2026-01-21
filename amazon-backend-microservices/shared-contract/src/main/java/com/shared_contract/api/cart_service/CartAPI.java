package com.shared_contract.api.cart_service;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shared_contract.dto.cart_service.CartDTO;
import com.shared_contract.dto.common_service.ResponseDTO;

public interface CartAPI {

	@GetMapping("cart/{productId}")
	CartDTO findCartByProductId(@PathVariable("productId") String productId);

	@PostMapping("cart/{productId}")
	ResponseDTO addToCart(@PathVariable("productId") String productId);

	@DeleteMapping("cart/{productId}")
	ResponseDTO deleteFromCart(@PathVariable("productId") String productId);
}
