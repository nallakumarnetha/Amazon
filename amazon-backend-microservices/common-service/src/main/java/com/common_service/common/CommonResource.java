package com.common_service.common;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shared_contract.original.Logger.log;


@RestController
@RequestMapping("common")
public class CommonResource {

	@Autowired
	private CommonService service;
	
	@GetMapping("dump-data")
	public Response dumpData() throws IOException {
		log.info("request recieved: dump data");
		service.dumpData();
		log.info("response sent: dump data");
		Response response = new Response();
		response.setMessage("dumped data!");
		return response;
	}
	
	@DeleteMapping("clear-data")
	public Response clearData() throws IOException {
		log.info("request recieved: clear data");
		service.clearData();
		log.info("response sent: clear data");
		Response response = new Response();
		response.setMessage("cleared data!");
		return response;
	}
	
}
