package com.common_service.client;

@FeignClient(name = "COMMON-SERVICE")
public interface CommonClient extends CommonAPI {

}
