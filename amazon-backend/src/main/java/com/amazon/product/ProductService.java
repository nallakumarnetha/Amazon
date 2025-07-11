package com.amazon.product;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amazon.common.MyAudit;
import com.amazon.common.MyResponse;

import jakarta.websocket.server.PathParam;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	// CRUD start

	public MyResponse findAllProducts(int page, int size) {
		// sorting
		Sort sort = Sort.by(Sort.Direction.DESC, "audit.modifiedDate");
		// paginaion
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> productPage = repository.findAll(pageable);
		List<Product> products = productPage.getContent();
		MyResponse response = new MyResponse();
		response.setProducts(products);
		return response;
	}

	public Product findProductById(String id) {
		Product response = repository.findById(id).orElse(null);
		return response;
	}

	public Product addProduct(Product request) {
		Product response = repository.save(request);
		return response;
	}

	public Product updateProduct(String id, Product request) {
		Product entity = repository.findById(id).orElse(null);
		if (request.getName() != null && !request.getName().isEmpty())
			entity.setName(request.getName());
		if (request.getPrice() != 0)
			entity.setPrice(request.getPrice());
		Product response = repository.save(entity);
		return response;
	}

	public MyResponse deleteProduct(String id) {
		repository.deleteById(id);
		MyResponse response = new MyResponse();
		return response;
	}

	// CRUD end

}
