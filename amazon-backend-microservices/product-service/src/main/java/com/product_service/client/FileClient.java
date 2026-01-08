package com.product_service.client;

import com.shared_contract.api.file_service.FileAPI;

@FeignClient(name = "FILE-SERVICE")
public interface FileClient extends FileAPI {

}
