package com.melo.eapp.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CartItemsDto {


	private Long id;
	
	private Long price;
	
	private Long quantiy;
	
	private long productId;
	
	private Long orderId;
	
	private String productName;
	
	private byte[] returnByteImg;	
	
	private Long userId;
}
