package com.common_service.client;

import com.shared_contract.api.product_service.ProductAPI;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient extends ProductAPI {

}
