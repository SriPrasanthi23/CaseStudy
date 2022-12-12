package com.eshoppingzone.CartService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eshoppingzone.CartService.entity.Cart;

@Repository
public interface CartRepository extends MongoRepository<Cart, Integer> {
	Cart findByCartId(int cartId);
}