package com.shared_contract.api.order_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.order_service.order.FilterRequest;
import com.order_service.order.Order;
import com.order_service.order.OrderService;
import com.order_service.order.Response;

@RequestMapping("orders")
public interface OrderAPI {

	@GetMapping
	ResponseDTO findAllOrders(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size);

	@GetMapping("/{id}")
	OrderDTO findOrderById(@PathVariable String id);

	@PostMapping
	OrderDTO addOrder(@RequestBody Order request);

	@PutMapping("/{id}")
	OrderDTO updateOrder(@PathVariable String id, @RequestBody Order request);

	@DeleteMapping("/{id}")
	ResponseDTO deleteOrder(@PathVariable String id);
	
	@GetMapping("/search")
	ResponseDTO searchOrders(@RequestParam String name);
	
	@PostMapping("/filter")
	ResponseDTO filterOrders(@RequestBody FilterRequest request);

}
