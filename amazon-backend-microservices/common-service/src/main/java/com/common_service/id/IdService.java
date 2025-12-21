package com.common_service.id;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdService {

	@Autowired
	private IdRepository repository;

	public synchronized String getProductID() {
		String response = null;
		List<ID> ids = repository.findAll();
		ID id = null;
		if (ids.isEmpty() && ids.size() == 0) {
			id = new ID();
			id.setProductCount(1);
			id.setUserCount(1);
			id.setOrderCount(1);
		} else {
			id = ids.get(0);
			id.setProductCount(id.getProductCount() + 1);
		}
		repository.save(id);
		response = "PRO_" + id.getProductCount();
		return response;
	}
	
	public synchronized String getUserID() {
		String response = null;
		List<ID> ids = repository.findAll();
		ID id = null;
		if (ids.isEmpty() && ids.size() == 0) {
			id = new ID();
			id.setProductCount(1);
			id.setUserCount(1);
			id.setOrderCount(1);
		} else {
			id = ids.get(0);
			id.setUserCount(id.getUserCount() + 1);
		}
		repository.save(id);
		response = "USR_" + id.getUserCount();
		return response;
	}
	
	public synchronized String getOrderID() {
		String response = null;
		List<ID> ids = repository.findAll();
		ID id = null;
		if (ids.isEmpty() && ids.size() == 0) {
			id = new ID();
			id.setProductCount(1);
			id.setUserCount(1);
			id.setOrderCount(1);
		} else {
			id = ids.get(0);
			id.setOrderCount(id.getOrderCount() + 1);
		}
		repository.save(id);
		response = "ORD_" + id.getOrderCount();
		return response;
	}
}
