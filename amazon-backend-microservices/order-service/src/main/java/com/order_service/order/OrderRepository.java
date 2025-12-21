package com.amazon.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amazon.product.Category;
import com.amazon.product.Product;

public interface OrderRepository extends JpaRepository<Order, String> {
	
	@Query("SELECT o FROM Order o WHERE LOWER(o.orderId) LIKE LOWER(:query)")
	List<Order> searchById(@Param("query") String query);
	
	@Query("SELECT o FROM Order o WHERE LOWER(o.productId) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(o.paymentId) LIKE LOWER(CONCAT('%', :query, '%'))")
	List<Order> searchByProductIdOrPaymentId(@Param("query") String query);

	List<Order> findByStatus(OrderStatus status);

	List<Order> findByAmountBetween(double minAmount, double maxAmount);

	List<Order> findByCategory(Category category);
	
	Page<Order> findByUserId(String userId, Pageable pageable);
	
	long countByUserId(String userId);


}
