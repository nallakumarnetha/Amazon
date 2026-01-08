package com.product_service.client;

import com.shared_contract.api.cart_service.CartAPI;

@FeignClient(name = "CART-SERVICE")
public interface CartClient extends CartAPI {

}
