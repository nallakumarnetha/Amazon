package com.shared_contract.api.order_service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shared_contract.dto.order_service.OrderDTO;

public interface OrderAPI {

	@PostMapping("orders")
	OrderDTO addOrder(@RequestBody OrderDTO request);

}
