package com.shared_contract.api.common_service;

import java.io.IOException;

@RequestMapping("common")
public interface CommonAPI {

	@GetMapping("dump-data")
	ResponseDTO dumpData();
	
	@DeleteMapping("clear-data")
	ResponseDTO clearData();
	
}
