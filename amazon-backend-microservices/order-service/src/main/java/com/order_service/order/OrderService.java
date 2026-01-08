package com.order_service.order;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.order_service.client.UserClient;
import com.order_service.id.IdService;
import com.shared_contract.dto.user_service.UserDTO;
import com.shared_contract.original.FilterRequest;
import com.shared_contract.original.order_service.OrderStatus;
import com.shared_contract.original.user_service.Role;

import jakarta.mail.internet.MimeMessage;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private IdService idService;

	@Autowired
	private UserClient userClient;

	@Autowired
	private JavaMailSender mailSender;

	public Response findAllOrders(int page, int size) {
		UserDTO user = userClient.getCurrentUser();
		List<Order> orders = null;
		long total = 0;
		Sort sort = Sort.by(Sort.Direction.DESC, "audit.modifiedDate");
		if(user.getRole() == Role.Admin) {
			orders = repository.findAll(PageRequest.of(page, size, sort)).getContent();
			total = repository.count();
		} else {
			orders = repository.findByUserId(userClient.getCurrentUser().getId(), PageRequest.of(page, size, sort)).getContent();
			total = repository.countByUserId(user.getId()); 
		}
		Response response = new Response();
		response.setOrders(orders);
		response.setTotal(total);
		return response;
	}

	public Order findOrder(String id) {
		return repository.findById(id).orElse(null);
	}

	public Order addOrder(Order order) {
		if(order.getStatus() == null) {
			order.setStatus(OrderStatus.Pending);
		}
		order.setOrderId(idService.getOrderID());
		order.setUserId(userClient.getCurrentUser().getId());
		Order entity = repository.save(order);
		// sendEmailOnPlaceorder(entity);
		return entity;
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
		orders.addAll(repository.searchById(query));
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
	private void sendEmailOnPlaceorder(Order entity) {
		try {
			UserDTO user = userClient.findUserById(entity.getUserId());
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true); // multipart
			helper.setTo(user.getEmail());
			helper.setSubject("Order Confirmation - #" + entity.getOrderId());
			String html =
					"<table style='width:100%;'>" +
							"<tr>" +
							"<td style='text-align:left;'>" +
							"<img src='cid:logoImage' alt='Logo' style='width:150px; height:auto;'/>" +
							"</td>" +
							"</tr>" +
							"<tr>" +
							"<td style='padding-top:20px;'>" +
							"<h2>Order Placed Successfully!</h2>" +
							"<p>Hi <b>" + user.getUserName() + "</b>,</p>" +
							"<p>Your order is confirmed.<br/>Please find the order details below.</p>" +
							"<p><b>Order ID:</b> " + entity.getOrderId() + "<br>" +
							"<b>Product ID:</b> " + entity.getProductId() + "<br>" +
							"<b>Count:</b> " + entity.getCount() + "<br>" +
							"<b>Amount:</b> " + entity.getAmount() + " Rs<br>" +
							"<b>Payment ID:</b> " + entity.getPaymentId() + "<br>" +
							"<b>Status:</b> " + entity.getStatus() + "<br>" +
							"<b>Category:</b> " + entity.getCategory() + "</p>" +
							"<p>Thank you for shopping with us! &#128522;</p>" +
							"</td>" +
							"</tr>" +
							"</table>";
			helper.setText(html, true);
			FileSystemResource res = new FileSystemResource(new File("src/main/resources/images/amazon.png"));
			helper.addInline("logoImage", res);
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}