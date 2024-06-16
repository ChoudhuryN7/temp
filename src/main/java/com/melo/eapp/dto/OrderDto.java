package com.melo.eapp.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.melo.eapp.Enum.OrderStatus;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class OrderDto {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	   
	private String OrderDescription;
	
	private Date date;
	
	private Long amount;

	private String payment;
	
	private OrderStatus orderStatus;
	
	private Long totalAmount;
	
	private Long discount;
	
	private UUID trackingId;
	

	private String userName;
	
	
	private List<CartItemsDto> cartItems;
}
