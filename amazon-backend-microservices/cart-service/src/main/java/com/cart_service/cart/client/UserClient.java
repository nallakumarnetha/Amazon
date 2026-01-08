package com.cart_service.cart.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.shared_contract.api.user_service.UserAPI;

@FeignClient(name = "USER-SERVICE")
public interface UserClient extends UserAPI {

}
