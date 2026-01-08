package com.shared_contract.api.cart_service;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("cart")
public interface CartAPI {

	@GetMapping
	ResponseDTO findProductsByUserId(@RequestParam(required = false, name = "status") Status status);
	
	@GetMapping("{productId}")
	CartDTO findCartByProductId(@PathVariable String productId);

	@PostMapping("{productId}")
	ResponseDTO addToCart(@PathVariable String productId);

	@DeleteMapping("{productId}")
	ResponseDTO deleteFromCart(@PathVariable String productId);

	@PutMapping("{productId}/increase")
	ResponseDTO increaseCount(@PathVariable String productId);
	
	@PutMapping("{productId}/decrease")
	ResponseDTO decreaseCount(@PathVariable String productId);
	
	@PutMapping("{productId}")
	ResponseDTO changeStatus(@PathVariable("productId") String productId, @RequestParam("status") Status status);

}
