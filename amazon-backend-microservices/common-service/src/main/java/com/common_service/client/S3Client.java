package com.common_service.client;

import org.springframework.cloud.openfeign.FeignClient;

import com.shared_contract.api.file_service.S3API;

@FeignClient(name = "FILE-SERVICE", contextId = "s3Client")
public interface S3Client extends S3API {

}
