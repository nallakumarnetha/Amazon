package com.cart_service.cart.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.shared_contract.api.product_service.ProductAPI;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient extends ProductAPI {

}
