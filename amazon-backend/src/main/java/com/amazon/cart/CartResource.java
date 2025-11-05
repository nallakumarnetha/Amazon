package com.amazon.cart;

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
@RequestMapping("cart")
public class CartResource {

	@Autowired
	private CartService service;
	
	@GetMapping
	public Response findProductsByUserId(@RequestParam(required = false, name = "status") Status status) {
		log.info("request recieved: find cart products by user id");
		Response response = service.findProductsByUserId(status);
		log.info("response sent: find cart products by user id");
		return response;
	}
	
	@GetMapping("{productId}")
	public Cart findCartByProductId(@PathVariable String productId) {
		log.info("request recieved: find cart by product id");
		Cart response = service.findCartByProductId(productId);
		log.info("response sent: find cart by product id");
		return response;
	}

	@PostMapping("{productId}")
	public Response addToCart(@PathVariable String productId) {
		log.info("request recieved: add product to cart");
		Response response = service.addToCart(productId);
		log.info("response sent: add product to cart");
		return response;
	}

	@DeleteMapping("{productId}")
	public Response deleteFromCart(@PathVariable String productId) {
		log.info("request recieved: delete product from cart");
		Response response = service.deleteFromCart(productId);
		log.info("response sent: delete product from cart");
		return response;
	}

	@PutMapping("{productId}/increase")
	public Response increaseCount(@PathVariable String productId) {
		log.info("request recieved: increase product count in cart");
		Response response = service.increaseCount(productId);
		log.info("response sent: increase product count in cart");
		return response;
	}
	
	@PutMapping("{productId}/decrease")
	public Response decreaseCount(@PathVariable String productId) {
		log.info("request recieved: decrease product count in cart");
		Response response = service.decreaseCount(productId);
		log.info("response sent: decrease product count in cart");
		return response;
	}
	
	@PutMapping("{productId}")
	public Response changeStatus(@PathVariable("productId") String productId, @RequestParam("status") Status status) {
		log.info("request recieved: change cart product status");
		Response response = service.changeStatus(productId, status);
		log.info("response sent: change cart product status");
		return response;
	}

}
