package com.amazon.product;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amazon.common.Audit;
//import com.amazon.common.ID;
import com.amazon.common.Response;
import com.amazon.file.File;
import com.amazon.file.FileRepository;
import com.amazon.file.FileService;
import com.amazon.id.IdService;

import jakarta.websocket.server.PathParam;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private IdService idService;

	// CRUD start

	public Response findAllProducts(int page, int size) {
		// sorting
		Sort sort = Sort.by(Sort.Direction.DESC, "audit.modifiedDate");
		// pagination
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> productPage = repository.findAll(pageable);
		List<Product> products = productPage.getContent();
		for(Product product : products) {
			List<String> fileIds = product.getFiles();
			Map<String, String> base64FilesData = fileService.getBase64Files(fileIds);
			product.setBase64Files(base64FilesData);
		}
		Response response = new Response();
		response.setProducts(products);
		//count
		long total = repository.count();
		response.setTotal(total);
		return response;
	}

	public Product findProduct(String id) {
		Product response = repository.findById(id).orElse(null);
		List<String> fileIds = response.getFiles();
		Map<String, String> base64FilesData = fileService.getBase64Files(fileIds);
		response.setBase64Files(base64FilesData);
		return response;
	}

	public Product addProduct(Product request) {
		request.setProductId(idService.getProductID());
		Product response = repository.save(request);
		return response;
	}

	public Product updateProduct(String id, Product request) {
		Product entity = repository.findById(id).orElse(null);
		if (request.getName() != null && !request.getName().isEmpty()) {
			entity.setName(request.getName());
		}
		if (request.getPrice() != 0) {
			entity.setPrice(request.getPrice());
		}
		entity.setFiles(request.getFiles());
		Product response = repository.save(entity);
		return response;
	}

	public Response deleteProduct(String id) {
		repository.deleteById(id);
		Response response = new Response();
		return response;
	}

	// CRUD end

}
