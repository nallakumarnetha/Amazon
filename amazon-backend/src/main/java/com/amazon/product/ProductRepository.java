package com.amazon.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amazon.common.Response;

import jakarta.websocket.server.PathParam;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	@Query("SELECT p FROM Product p WHERE LOWER(p.productId) LIKE LOWER(:query) OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))")
	List<Product> searchByIdOrName(@Param("query") String query);

	List<Product> findByPriceBetween(double min, double max);
	List<Product> findByCountBetween(long min, long max);
	List<Product> findByCategory(Category category);
	
}
