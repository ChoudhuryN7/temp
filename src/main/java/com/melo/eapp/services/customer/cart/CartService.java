package com.melo.eapp.services.customer.cart;

import org.springframework.http.ResponseEntity;

import com.melo.eapp.dto.OrderDto;
import com.melo.eapp.entity.AddProductToCart;

public interface CartService {
	
	ResponseEntity<?> addProductToCart(AddProductToCart addProductToCartDto);
	
	OrderDto getCartByUserId(Long userId);

}
