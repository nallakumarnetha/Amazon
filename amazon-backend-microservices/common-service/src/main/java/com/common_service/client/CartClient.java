package com.common_service.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.shared_contract.api.cart_service.CartAPI;


@FeignClient(name = "CART-SERVICE")
public interface CartClient extends CartAPI {

}
