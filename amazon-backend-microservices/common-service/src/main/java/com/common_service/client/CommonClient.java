package com.common_service.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.shared_contract.api.common_service.CommonAPI;
import com.shared_contract.api.common_service.PreferencesAPI;

@FeignClient(name = "COMMON-SERVICE", contextId = "commonClient")
public interface CommonClient extends CommonAPI {

}
