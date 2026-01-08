package com.common_service.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.shared_contract.api.common_service.CommonAPI;

@FeignClient(name = "COMMON-SERVICE")
public interface CommonClient extends CommonAPI {

}
