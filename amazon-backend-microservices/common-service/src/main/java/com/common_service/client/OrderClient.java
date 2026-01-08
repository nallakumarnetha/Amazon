package com.common_service.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.shared_contract.api.order_service.OrderAPI;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient extends OrderAPI {

}
