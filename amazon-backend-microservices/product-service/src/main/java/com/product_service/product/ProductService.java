package com.amazon.product;

import static com.amazon.common.Logger.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;

import com.amazon.cart.Cart;
import com.amazon.cart.CartRepository;
import com.amazon.cart.CartService;
import com.amazon.common.Audit;
import com.amazon.common.CustomMultipartFile;
import com.amazon.common.FilterRequest;
//import com.amazon.common.ID;
import com.amazon.common.Response;
import com.amazon.file.File;
import com.amazon.file.FileRepository;
import com.amazon.file.FileService;
import com.amazon.id.IdService;
import com.amazon.user.UserService;

import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private FileService fileService;

	@Autowired
	private IdService idService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;

	// CRUD start

	public Response findAllProducts(int page, int size) {
		// sorting
		Sort sort = Sort.by(Sort.Direction.DESC, "audit.modifiedDate");
		// pagination
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> productPage = repository.findAll(pageable);
		List<Product> products = productPage.getContent();
		for(Product product : products) {
			// set base64 files
			List<String> fileIds = product.getFiles();
			Map<String, String> base64FilesData = fileService.getBase64Files(fileIds);
			product.setBase64Files(base64FilesData);
			//set cart count
			if(userService.getCurrentUser() != null) {
				Cart cart = cartService.findCartByProductId(product.getId());
				if(cart != null) {
					product.setCartCount(cart.getCount());
				}
			}
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
		// set base64 files
		List<String> fileIds = response.getFiles();
		Map<String, String> base64FilesData = fileService.getBase64Files(fileIds);
		response.setBase64Files(base64FilesData);
		//set cart count
		Cart cart = cartService.findCartByProductId(id);
		if(cart != null) {
			response.setCartCount(cart.getCount());
		}
		return response;
	}

	public Product addProduct(Product request) {
		request.setProductId(idService.getProductID());
		if(request.getName() != null || request.getName().length() < 95) {
			StringBuilder sb = new StringBuilder();
			sb.append(request.getName());
			sb.append(" | Best Quality Product Available at Great Price | Get the Best Deals on Top Brands Today | Only Limited Time");
			request.setName(sb.toString());
		}
		if(request.getFiles() == null || request.getFiles().isEmpty()) {
			addSampleImages(request);
		}
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
		if (request.getCategory() != null) {
			entity.setCategory(request.getCategory());
		}
		entity.setCount(request.getCount());
		entity.setFiles(request.getFiles());
		Product response = repository.save(entity);
		return response;
	}

	@Transactional
	public Response deleteProduct(String id) {
		cartRepository.deleteByProductId(id);
		repository.deleteById(id);
		Response response = new Response();
		response.setMessage("product deleted");
		return response;
	}

	// CRUD end

	public Response searchProducts(String query) {
		List<Product> products = repository.searchByIdOrName(query);
		for (Product product : products) {
			List<String> fileIds = product.getFiles();
			Map<String, String> base64FilesData = fileService.getBase64Files(fileIds);
			product.setBase64Files(base64FilesData);
		}
		Response response = new Response();
		response.setProducts(products);
		response.setTotal(products.size());
		return response;
	}

	public Response filterProducts(FilterRequest request) {
		List<Product> products = null;
		if(request.getMinPrice() != 0 || request.getMaxPrice() != 0) {
			products = repository.findByPriceBetween(request.getMinPrice(), request.getMaxPrice());
		}
		if(request.getMinCount() != 0 || request.getMaxCount() != 0) {
			products = repository.findByCountBetween(request.getMinCount(), request.getMaxCount());
		}
		if(request.getCategory() != null) {
			products = repository.findByCategory(request.getCategory());
		}
		for(Product product : products) {
			List<String> fileIds = product.getFiles();
			Map<String, String> base64FilesData = fileService.getBase64Files(fileIds);
			product.setBase64Files(base64FilesData);
		}
		Response response = new Response();
		response.setProducts(products);
		response.setTotal(products.size());
		return response;
	}

	public void addSampleImages(Product request) {
		try {
			Path path = Paths.get("src/main/resources/images/sampleProduct.png");
			byte[] imageBytes = Files.readAllBytes(path);
			MultipartFile multipartFile = new CustomMultipartFile("files", "file1.txt", "image/png", imageBytes);
			List<String> fileIds = fileService.uploadFile(List.of(multipartFile));
			request.setFiles(fileIds);
		} catch(Exception ex) {
			log.info("failed to add sample image");
		}
	}

}
