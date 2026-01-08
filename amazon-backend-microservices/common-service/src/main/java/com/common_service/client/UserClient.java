package com.common_service.client;

import com.shared_contract.api.user_service.UserAPI;

@FeignClient(name = "USER-SERVICE")
public interface UserClient extends UserAPI {

}
