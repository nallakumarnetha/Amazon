package com.amazon.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazon.common.Response;
import com.amazon.common.Status;
import com.amazon.product.Product;
import com.amazon.product.ProductRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository repository;

	@Autowired
	private ProductRepository productRepository;

	static String userId = "u1";	//to do

	public Response findProductsByUserId(Status status) {
		List<Product> products = new ArrayList<>();
		List<Cart> cartProducts = null;
		if(status == null) {
			cartProducts = repository.findByUserId(userId);
		} else {
			cartProducts = repository.findByUserIdAndStatus(userId, status);
		}
		cartProducts.forEach(p -> { 
			Product product = productRepository.findById(p.getProductId()).orElse(null);
			product.setCartCount(p.getCount());
			product.setStatus(p.getStatus());
			products.add(product);
		});
		Response response = new Response();
		response.setProducts(products);
		return response;
	}

	public Response addToCart(String productId) {
		Response response = new Response();
		Cart cart = repository.findByUserIdAndProductId(userId, productId);
		if(cart == null) {
			Cart newCart = new Cart();
			newCart.setUserId(userId);
			newCart.setProductId(productId);
			newCart.setStatus(Status.Active);
			newCart.setCount(1);
			repository.save(newCart);
		}
		return response;
	}

	public Response deleteFromCart(String productId) {
		Cart cart = repository.findByUserIdAndProductId(userId, productId);
		if (cart != null) {
			repository.delete(cart);
		}
		Response response = new Response();
		response.setMessage("cart product deleted");
		return response;
	}

	public Response increaseCount(String productId) {
		Response response = new Response();
		Cart cart = repository.findByUserIdAndProductId(userId, productId);
		if (cart != null) {
			cart.setCount(cart.getCount() + 1);
			repository.save(cart);
		}
		response.setMessage("Product count increased");
		return response;
	}

	public Response decreaseCount(String productId) {
		Response response = new Response();
		Cart cart = repository.findByUserIdAndProductId(userId, productId);
		if (cart != null && cart.getCount() > 1) {
			cart.setCount(cart.getCount() - 1);
			repository.save(cart);
			response.setMessage("Product count decreased");
		} else if (cart != null && cart.getCount() <= 1) {
			repository.delete(cart);
			response.setMessage("Product removed from cart");
		}
		return response;
	}
	
	public Response changeStatus(String productId, Status status) {
		Response response = new Response();
		Cart cart = repository.findByUserIdAndProductId(userId, productId);
		if (cart != null) {
			cart.setStatus(status);
			repository.save(cart);
			response.setMessage("cart product selected/deselected");
		}
		return response;
	}

}
