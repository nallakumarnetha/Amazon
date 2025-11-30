package com.amazon.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.cart.CartService;
import com.amazon.file.FileService;
import com.amazon.order.Order;
import com.amazon.order.OrderService;
import com.amazon.order.OrderStatus;
import com.amazon.product.Category;
import com.amazon.product.Product;
import com.amazon.product.ProductService;
import com.amazon.user.Address;
import com.amazon.user.Gender;
import com.amazon.user.Language;
import com.amazon.user.Role;
import com.amazon.user.User;
import com.amazon.user.UserService;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@Service
public class CommonService {

	@Autowired
	private ProductService productService;

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;
	@Autowired

	private OrderService orderService;
	
	@Autowired
	private EntityManager entityManager;

	public void dumpData() throws IOException {
		List<String> cart = new ArrayList<>();
		List<String> orders = new ArrayList<>();
		// ----------------users-----------------------
		/*
		// admin
		Path path = Paths.get("src/main/resources/images/kumar_admin.png");
		byte[] imageBytes = Files.readAllBytes(path);
		MultipartFile multipartFile = new CustomMultipartFile("file", "arts.png", "image/png", imageBytes);
		List<String> fileIds = fileService.uploadFile(List.of(multipartFile));
		
		User user = new User();
		user.setFirstName("Kumar");
		user.setLastName("Nalla");
		user.setName("Kumar Nalla");
		user.setPhoneNumber("+91 8498931574");
		user.setGender(Gender.Male);
			Address address = new Address();
			address.setStreet("Highschool colony");
			address.setCity("Begumpeta");
			address.setPincode("508105");
		user.setAddress(address);
		user.setRole(Role.Admin);
		user.setLanguage(Language.Telugu);
		user.setFiles(fileIds);
		user = userService.addUser(user, null);
	
		// end-user
		path = Paths.get("src/main/resources/images/kumar_enduser.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("file", "arts.jpg", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));
		
		user = new User();
		user.setFirstName("Kumar");
		user.setLastName("Thella");
		user.setName("Kumar Thella");
		user.setPhoneNumber("+91 6305252149");
		user.setGender(Gender.Male);
			address = new Address();
			address.setStreet("Durgam cheruvu");
			address.setCity("Madhapur");
			address.setPincode("500081");
		user.setAddress(address);
		user.setRole(Role.Enduser);
		user.setLanguage(Language.Telugu);
		user.setFiles(fileIds);
		user = userService.addUser(user, null);
		*/
		// ----------------products-----------------------
//		Path path = Paths.get("src/main/resources/images/kumar_admin.png");
//		byte[] imageBytes = Files.readAllBytes(path);
//		MultipartFile multipartFile = new CustomMultipartFile("file", "arts.png", "image/png", imageBytes);
//		List<String> fileIds = fileService.uploadFile(List.of(multipartFile));
		
		Path path = null;
		byte[] imageBytes = null;
		MultipartFile multipartFile = null;
		List<String> fileIds = null;
		User user = userService.getCurrentUser();
		Address	address = user.getAddress();

		// product 1
		path = Paths.get("src/main/resources/images/arts.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "file1.txt", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		Product product = new Product();
		product.setName("Caliart Watercolor Paint Set, 100 Colors Portable Travel Water Color Palette Kit with Detail Painting Brush, Art Supplies Christmas Gifts for Artists Adults Teen Girls Boys Beginners");
		product.setPrice(1951);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Arts);
		product = productService.addProduct(product);
		
		cart.add(product.getId());
		cart.add(product.getId());
		
		// product 2
		path = Paths.get("src/main/resources/images/baby.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "baby.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("Growsland Remote Control Car, RC Cars for Kids 1:18 Electric Toy Car Hobby Racing Car Toys with Lights & Controller, Christmas Birthday Gift for 3 4 5 6 7 8 9 Year Old Boys Girls");
		product.setPrice(1507);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Baby);
		product = productService.addProduct(product);
		
		cart.add(product.getId());
		cart.add(product.getId());
		
		// product 3
		path = Paths.get("src/main/resources/images/beauty.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "beauty.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("Color Nymph Face Makeup Kits, Makeup Gift Sets For Girls Make up Gift Sets For Women With Lipgloss Liquid Blush Solid Blusher Highlighters Contour Stick and Double-ended Brush -Special Edition");
		product.setPrice(2321);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Beauty);
		product = productService.addProduct(product);
		
		cart.add(product.getId());
		cart.add(product.getId());
		
		// product 4
		path = Paths.get("src/main/resources/images/books.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "books.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("Aju Publications Amma Diarylo Konni Pageelu");
		product.setPrice(1462);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Books);
		product = productService.addProduct(product);

		orders.add(product.getId());
		
		// product 5
		path = Paths.get("src/main/resources/images/clothes.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "clothes.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("PJ PAUL JONES Mens Casual Sport Coat Blazer Two Buttons Lightweight Business Jackets");
		product.setPrice(5933);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Clothes);
		product = productService.addProduct(product);
		
		orders.add(product.getId());
		
		// product 6
		path = Paths.get("src/main/resources/images/electronics.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "electronics.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("HP Portable Laptop, Student and Business, 14\" HD Display, Intel Quad-Core N4120, 8GB DDR4 RAM, 64GB eMMC, 1 Year Office 365, Webcam, RJ-45, HDMI, Wi-Fi, Windows 11 Home, Silver");
		product.setPrice(12727);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Electronics);
		product = productService.addProduct(product);
		
		orders.add(product.getId());
		
		// product 7
		path = Paths.get("src/main/resources/images/footwear.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "footwear.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("BENPAO Mens White Casual Shoes All Black Fashion Sneakers for Men Low Top Lace Up Canvas Shoes");
		product.setPrice(2235);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Footwear);
		product = productService.addProduct(product);
		
		orders.add(product.getId());
		
		// product 8
		path = Paths.get("src/main/resources/images/furniture.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "furniture.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("4 Piece T Cushion Sofa Slipcovers Stretch Couch Cover Furniture Covers with 3 Individual T Cushion Covers, Machine Washable for Living Room (Green, 3 Cushions)");
		product.setPrice(4729);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Furniture);
		product = productService.addProduct(product);
		
		orders.add(product.getId());
		
		// product 9
		path = Paths.get("src/main/resources/images/grocery.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "grocery.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("Creative Green Life Reusable Grocery Bags (3 Pack) – Heavy Duty Reusable Shopping Bags with Box Shape to Stand Up, Stay Open, Fold Flat – Large Tote Bags with Long Handles & Reinforced Bottom (Gray)");
		product.setPrice(2493);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Grocery);
		product = productService.addProduct(product);
		
		orders.add(product.getId());
		
		// product 10
		path = Paths.get("src/main/resources/images/home_appliances.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "home_appliances.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("Extra Large Expandable Silverware Organizer, BPA-Free Food-Safe Cutlery Flatware Organizer, Kitchen Utensil Drawer Organizer, Adjustable Silverware Holder for Spoons Forks Knives, Black");
		product.setPrice(515);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Home_appliances);
		product = productService.addProduct(product);
		
		// product 11
		path = Paths.get("src/main/resources/images/men.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "men.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));

		product = new Product();
		product.setName("BEN NEVIS Watch, Mens Watch, Minimalist Fashion Simple Wrist Watch Analog Date with Leather Strap");
		product.setPrice(1836);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Men);
		product = productService.addProduct(product);
		
		// product 12
		path = Paths.get("src/main/resources/images/women.png");
		imageBytes = Files.readAllBytes(path);
		multipartFile = new CustomMultipartFile("files", "women.png", "image/png", imageBytes);
		fileIds = fileService.uploadFile(List.of(multipartFile));
		
		product = new Product();
		product.setName("Roulens Small Crossbody Bag for Women,Cell Phone Purse Women's Shoulder Handbags Wallet Purse with Credit Card Slots");
		product.setPrice(1977);
		product.setFiles(fileIds);
		product.setCount(10);
		product.setCategory(Category.Women);
		product = productService.addProduct(product);
		// ----------------cart products-----------------------
		cart.forEach(productId -> cartService.addToCart(productId));
		
		// ----------------orders-----------------------
		// order 1
		Order order = new Order();
		order.setUserId(user.getId());
		
		order.setProductId(productService.findProduct(orders.get(0)).getProductId());
		order.setCount(2);
		order.setAmount(2924);
		order.setPaymentId("p_id_1");
		order.setStatus(OrderStatus.Pending);
		order.setAddress(address.getCity());
		order.setCategory(Category.Books);
		order = orderService.addOrder(order);
		
		// order 2
		order = new Order();
		order.setUserId(user.getId());
		order.setProductId(productService.findProduct(orders.get(1)).getProductId());
		order.setCount(2);
		order.setAmount(11866);
		order.setPaymentId("p_id_2");
		order.setStatus(OrderStatus.Processing);
		order.setAddress(address.getCity());
		order.setCategory(Category.Clothes);
		order = orderService.addOrder(order);
		
		// order 3
		order = new Order();
		order.setUserId(user.getId());
		order.setProductId(productService.findProduct(orders.get(2)).getProductId());
		order.setCount(2);
		order.setAmount(25454);
		order.setPaymentId("p_id_3");
		order.setStatus(OrderStatus.Shipped);
		order.setAddress(address.getCity());
		order.setCategory(Category.Electronics);
		order = orderService.addOrder(order);
		
		// order 4
		order = new Order();
		order.setUserId(user.getId());
		order.setProductId(productService.findProduct(orders.get(3)).getProductId());
		order.setCount(2);
		order.setAmount(4470);
		order.setPaymentId("p_id_4");
		order.setStatus(OrderStatus.Delivered);
		order.setAddress(address.getCity());
		order.setCategory(Category.Footwear);
		order = orderService.addOrder(order);
		
		// order 5
		order = new Order();
		order.setUserId(user.getId());
		order.setProductId(productService.findProduct(orders.get(4)).getProductId());
		order.setCount(2);
		order.setAmount(9458);
		order.setPaymentId("p_id_5");
		order.setStatus(OrderStatus.Cancelled);
		order.setAddress(address.getCity());
		order.setCategory(Category.Furniture);
		order = orderService.addOrder(order);
		
		// order 6
		order = new Order();
		order.setUserId(user.getId());
		order.setProductId(productService.findProduct(orders.get(5)).getProductId());
		order.setCount(2);
		order.setAmount(4986);
		order.setPaymentId("p_id_6");
		order.setStatus(OrderStatus.Returned);
		order.setAddress(address.getCity());
		order.setCategory(Category.Grocery);
		order = orderService.addOrder(order);
	}
	
	@Transactional
	public void clearData() {
	    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();

	    entityManager.createNativeQuery("TRUNCATE TABLE product").executeUpdate();
	    entityManager.createNativeQuery("TRUNCATE TABLE cart").executeUpdate();
	    entityManager.createNativeQuery("TRUNCATE TABLE orders").executeUpdate();

	    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
	}

}

