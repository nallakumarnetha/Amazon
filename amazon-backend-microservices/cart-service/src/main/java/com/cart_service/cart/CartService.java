package com.cart_service.cart;

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
import com.amazon.file.FileService;
import com.amazon.product.Product;
import com.amazon.product.ProductRepository;
import com.amazon.user.UserService;
import com.cart_service.cart.client.FileClient;
import com.cart_service.cart.client.ProductClient;

@Service
public class CartService {

	@Autowired
	private CartRepository repository;

	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private FileClient fileClient;
	
	@Autowired
	private UserClient userClient;

	public Response findProductsByUserId(Status status) {
		List<ProductDTO> products = new ArrayList<>();
		List<Cart> cartProducts = null;
		if(status == null) {
			cartProducts = repository.findByUserId(userClient.getCurrentUser().getId());
		} else {
			cartProducts = repository.findByUserIdAndStatus(userClient.getCurrentUser().getId(), status);
		}
		cartProducts.forEach(p -> { 
			ProductDTO product = productClient.findById(p.getProductId()).orElse(null);
			product.setCartCount(p.getCount());
			product.setStatus(p.getStatus());
			List<String> fileIds = product.getFiles();
			Map<String, String> base64FilesData = fileClient.getBase64Files(fileIds);
			product.setBase64Files(base64FilesData);
			products.add(product);
		});
		Response response = new Response();
		response.setProducts(products);
		response.setTotal(products.size());
		return response;
	}

	public Response addToCart(String productId) {
		Response response = new Response();
		Cart cart = repository.findByUserIdAndProductId(userClient.getCurrentUser().getId(), productId);
		if(cart == null) {
			Cart newCart = new Cart();
			newCart.setUserId(userClient.getCurrentUser().getId());
			newCart.setProductId(productId);
			newCart.setStatus(Status.Active);
			newCart.setCount(1);
			repository.save(newCart);
		}
		return response;
	}

	public Response deleteFromCart(String productId) {
		Cart cart = repository.findByUserIdAndProductId(userClient.getCurrentUser().getId(), productId);
		if (cart != null) {
			repository.delete(cart);
		}
		Response response = new Response();
		response.setMessage("cart product deleted");
		return response;
	}

	public Response increaseCount(String productId) {
		Response response = new Response();
		Cart cart = repository.findByUserIdAndProductId(userClient.getCurrentUser().getId(), productId);
		if (cart != null) {
			cart.setCount(cart.getCount() + 1);
			repository.save(cart);
		}
		response.setMessage("Product count increased");
		return response;
	}

	public Response decreaseCount(String productId) {
		Response response = new Response();
		Cart cart = repository.findByUserIdAndProductId(userClient.getCurrentUser().getId(), productId);
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
		Cart cart = repository.findByUserIdAndProductId(userClient.getCurrentUser().getId(), productId);
		if (cart != null) {
			cart.setStatus(status);
			repository.save(cart);
			response.setMessage("cart product selected/deselected");
		}
		return response;
	}
	
	public Cart findCartByProductId(String productId) {
		Cart response = repository.findByProductIdAndUserId(productId, userClient.getCurrentUser().getId());
		return response;
	}

}
