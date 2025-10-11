package com.amazon.id;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdService {

	@Autowired
	private IdRepository repository;

	public String getProductID() {
		String response = null;
		List<ID> ids = repository.findAll();
		ID id = null;
		if (ids.isEmpty() && ids.size() == 0) {
			id = new ID();
			id.setProductCount(1);
			id.setUserCount(1);
			ID res = repository.save(id);
			response = "PRO_" + res.getProductCount();
		} else {
			id = ids.get(0);
			id.setProductCount(id.getProductCount() + 1);
			repository.save(id);
			response = "PRO_" + id.getProductCount();
		}
		return response;
	}
	
	public String getUserID() {
		String response = null;
		List<ID> ids = repository.findAll();
		ID id = null;
		if (ids.isEmpty() && ids.size() == 0) {
			id = new ID();
			id.setProductCount(1);
			id.setUserCount(1);
			ID res = repository.save(id);
			response = "USR_" + res.getUserCount();
		} else {
			id = ids.get(0);
			id.setUserCount(id.getUserCount() + 1);
			repository.save(id);
			response = "USR_" + id.getUserCount();
		}
		return response;
	}
}
