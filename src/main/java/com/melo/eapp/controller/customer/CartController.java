package com.melo.eapp.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.eapp.dto.OrderDto;
import com.melo.eapp.entity.AddProductToCart;
import com.melo.eapp.services.customer.CustomerProductService;
import com.melo.eapp.services.customer.cart.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {

	
	private final CartService cartService;

   
	@PostMapping("/cart")
	public ResponseEntity<?> addProductToCart(@RequestBody AddProductToCart addProductToCart)
	{
		return cartService.addProductToCart(addProductToCart);
	}
	
	@GetMapping("/cart/{userId}")
	public ResponseEntity<?> getCartByUserId(@PathVariable Long userId)
	{
		OrderDto orderDto = cartService.getCartByUserId(userId);
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}
	
	@PostMapping("/addition")
	public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProductToCart addProductToCart)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductToCart));
	}
}


