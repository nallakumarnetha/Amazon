package com.amazon.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.common.Response;

import jakarta.websocket.server.PathParam;

public interface ProductRepository extends JpaRepository<Product, String> {

}
