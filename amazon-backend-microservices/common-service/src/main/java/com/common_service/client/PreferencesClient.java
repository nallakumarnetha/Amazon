package com.common_service.client;

import com.shared_contract.api.common_service.PreferencesAPI;

@FeignClient(name = "COMMON-SERVICE")
public interface PreferencesClient extends PreferencesAPI {

}
