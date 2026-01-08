package com.cart_service.cart.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.shared_contract.api.file_service.FileAPI;

@FeignClient(name = "FILE-SERVICE")
public interface FileClient extends FileAPI {

}
