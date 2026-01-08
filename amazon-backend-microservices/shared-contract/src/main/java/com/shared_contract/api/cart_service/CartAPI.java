package com.shared_contract.api.cart_service;

import static com.shared_contract.original.Logger.log;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shared_contract.dto.cart_service.CartDTO;
import com.shared_contract.dto.common_service.ResponseDTO;

@RequestMapping("cart")
public interface CartAPI {

	@GetMapping("{productId}")
	CartDTO findCartByProductId(@PathVariable String productId);

	@PostMapping("{productId}")
	ResponseDTO addToCart(@PathVariable String productId);

	@DeleteMapping("{productId}")
	ResponseDTO deleteFromCart(@PathVariable String productId);
}
