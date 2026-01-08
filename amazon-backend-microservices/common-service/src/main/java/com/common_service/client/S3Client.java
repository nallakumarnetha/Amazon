package com.common_service.client;

@FeignClient(name = "FILE-SERVICE")
public interface S3Client extends S3API {

}
