package com.amazon.address;

import static com.amazon.common.Logger.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.amazon.common.Response;

@RestController
@RequestMapping("addresses")
//@CrossOrigin
public class AddressResource {

	@Autowired
	private AddressService service;

	@GetMapping
	public Response findAll(@RequestParam(defaultValue = "0") int page,
	                        @RequestParam(defaultValue = "10") int size) {
		log.info("request received: find all addresses");
		Response response = service.findAllAddresses(page, size);
		log.info("response sent: find all addresses");
		return response;
	}

	@GetMapping("/{id}")
	public Address findById(@PathVariable String id) {
		log.info("request received: find address by id");
		Address response = service.findAddressById(id);
		log.info("response sent: find address by id");
		return response;
	}

	@PostMapping
	public Address addAddress(@RequestBody Address request) {
		log.info("request received: add address");
		Address response = service.addAddress(request);
		log.info("response sent: add address");
		return response;
	}

	@PutMapping("/{id}")
	public Address updateAddress(@PathVariable String id, @RequestBody Address request) {
		log.info("request received: update address");
		Address response = service.updateAddress(id, request);
		log.info("response sent: update address");
		return response;
	}

	@DeleteMapping("/{id}")
	public Response deleteAddress(@PathVariable String id) {
		log.info("request received: delete address");
		Response response = service.deleteAddress(id);
		log.info("response sent: delete address");
		return response;
	}

//	@GetMapping("/search")
//	public Response search(@RequestParam String query) {
//		log.info("request received: search address {}", query);
//		Response response = service.searchAddresses(query);
//		log.info("response sent: search address");
//		return response;
//	}
}
