package com.amazon.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amazon.common.FilterRequest;
import com.amazon.common.Response;
import com.amazon.id.IdService;
import com.amazon.product.Product;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private IdService idService;
	
	static String userId = "u1";	//to do

	public Response findAllOrders(int page, int size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "audit.modifiedDate");
		List<Order> orders = repository.findAll(PageRequest.of(page, size, sort)).getContent();
		Response response = new Response();
		response.setOrders(orders);
		long total = repository.count();
		response.setTotal(total);
		return response;
	}

	public Order findOrder(String id) {
		return repository.findById(id).orElse(null);
	}

	public Order addOrder(Order order) {
		order.setOrderId(idService.getOrderID());
		order.setUserId(userId);
		return repository.save(order);
	}

	public Order updateOrder(String id, Order order) {
		if (repository.existsById(id)) {
			order.setId(id);
			return repository.save(order);
		}
		return null;
	}

	public Response deleteOrder(String id) {
		repository.deleteById(id);
		Response response = new Response();
		response.setMessage("Order deleted successfully");
		return response;
	}
	
	public Response searchOrders(String query) {
	    List<Order> orders = repository.searchByProductIdOrPaymentId(query);
	    Response response = new Response();
	    response.setOrders(orders);
	    response.setTotal(orders.size());
	    return response;
	}
	
	public Response filterOrders(FilterRequest request) {
	    List<Order> orders = null;
	    if(request.getMinPrice() != 0 || request.getMaxPrice() != 0) {
	    	orders = repository.findByAmountBetween(request.getMinPrice(), request.getMaxPrice());
	    }
	    if(request.getOrderStatus() != null) {
	    	orders = repository.findByStatus(request.getOrderStatus());
	    }
	    if(request.getCategory() != null) {
	    	orders = repository.findByCategory(request.getCategory());
	    }
		Response response = new Response();
		response.setOrders(orders);
	    response.setTotal(orders.size());
	    return response;
	}

}
