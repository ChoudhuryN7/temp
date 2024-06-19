package com.melo.eapp.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.melo.eapp.Enum.OrderStatus;
import com.melo.eapp.dto.OrderDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order {
	
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
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private Users user;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
	private List<CartItems> cartItems;
	
	
	public OrderDto getOrderDto() {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(id);
		orderDto.setOrderDescription(OrderDescription);
		orderDto.setDate(date);
		orderDto.setAmount(amount);
		orderDto.setPayment(payment);
		orderDto.setOrderStatus(orderStatus);
		orderDto.setTotalAmount(totalAmount);
		orderDto.setDiscount(discount);
		orderDto.setTrackingId(trackingId);
		orderDto.setUserName(this.user.getName());
		orderDto.setCartItems(cartItems.stream().map(ele ->ele.getCartDto()).collect(Collectors.toList()));

		return orderDto;
	}
}
