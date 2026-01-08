package com.cart_service.cart;

import java.util.List;
import com.shared_contract.original.Status;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
	public List<Cart> findByUserId(String id);
	public Cart findByProductIdAndUserId(String productId, String userId);
	public List<Cart> findByUserIdAndStatus(String id, Status status);
	public Cart findByUserIdAndProductId(String userId, String productId);
	public void deleteByProductId(String id);
}
