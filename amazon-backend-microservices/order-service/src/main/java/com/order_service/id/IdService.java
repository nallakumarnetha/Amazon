package com.order_service.id;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdService {

	@Autowired
	private IdRepository repository;

	public synchronized String getOrderID() {
		String response = null;
		List<ID> ids = repository.findAll();
		ID id = null;
		if (ids.isEmpty() && ids.size() == 0) {
			id = new ID();
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
