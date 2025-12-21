package com.order_service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.common.FilterRequest;
import com.amazon.common.Response;
import static com.amazon.common.Logger.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.common.Response;
import com.amazon.common.Status;
import com.amazon.product.Product;
import com.amazon.product.ProductService;

@RestController
@RequestMapping("orders")
public class OrderResource {

	@Autowired
	private OrderService service;

	@GetMapping
	public Response findAllOrders(@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		log.info("request received: find all orders");
		Response response = service.findAllOrders(page, size);
		log.info("response sent: find all orders");
		return response;
	}

	@GetMapping("/{id}")
	public Order findOrderById(@PathVariable String id) {
		log.info("request received: find order by id");
		Order response = service.findOrder(id);
		log.info("response sent: find order by id");
		return response;
	}

	@PostMapping
	public Order addOrder(@RequestBody Order request) {
		log.info("request received: add order");
		Order response = service.addOrder(request);
		log.info("response sent: add order");
		return response;
	}

	@PutMapping("/{id}")
	public Order updateOrder(@PathVariable String id, @RequestBody Order request) {
		log.info("request received: update order");
		Order response = service.updateOrder(id, request);
		log.info("response sent: update order");
		return response;
	}

	@DeleteMapping("/{id}")
	public Response deleteOrder(@PathVariable String id) {
		log.info("request received: delete order");
		Response response = service.deleteOrder(id);
		log.info("response sent: delete order");
		return response;
	}
	
	@GetMapping("/search")
	public Response searchOrders(@RequestParam String name) {
	    log.info("Search request received: {}", name);
	    return service.searchOrders(name);
	}
	
	@PostMapping("/filter")
	public Response filterOrders(@RequestBody FilterRequest request) {
	    log.info("filter request received");
	    return service.filterOrders(request);
	}

}
