package com.amazon.address;

import static com.amazon.common.Logger.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amazon.common.Response;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;

	// Find all with pagination
	public Response findAllAddresses(int page, int size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "audit.modifiedDate");
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Address> pageData = repository.findAll(pageable);

		Response response = new Response();
		response.setAddresses(pageData.getContent());
		response.setTotal(repository.count());
		return response;
	}

	// Find by ID
	public Address findAddressById(String id) {
		return repository.findById(id).orElse(null);
	}

	// Add new
	public Address addAddress(Address request) {
		return repository.save(request);
	}

	// Update
	public Address updateAddress(String id, Address request) {
		Address entity = repository.findById(id).orElse(null);
		if (entity == null) return null;

		if (request.getStreet() != null) entity.setStreet(request.getStreet());
		if (request.getCity() != null) entity.setCity(request.getCity());
		if (request.getPincode() != null) entity.setPincode(request.getPincode());

		return repository.save(entity);
	}

	// Delete
	public Response deleteAddress(String id) {
		repository.deleteById(id);
		Response response = new Response();
		response.setMessage("address deleted");
		return response;
	}

	// Search
//	public Response searchAddresses(String query) {
//		List<Address> list = repository.searchByCityOrPincode(query);
//		Response response = new Response();
//		response.setAddresses(list);
//		response.setTotal(list.size());
//		return response;
//	}
}
