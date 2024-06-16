package com.melo.eapp.entity;

import lombok.Data;

@Data
public class AddProductToCart {

	private Long userId;
	
	private Long productId;
}
