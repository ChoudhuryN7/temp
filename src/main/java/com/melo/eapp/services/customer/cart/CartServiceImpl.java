package com.melo.eapp.services.customer.cart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.melo.eapp.Enum.OrderStatus;
import com.melo.eapp.dto.CartItemsDto;
import com.melo.eapp.dto.OrderDto;
import com.melo.eapp.entity.AddProductToCart;
import com.melo.eapp.entity.CartItems;
import com.melo.eapp.entity.Order;
import com.melo.eapp.entity.Product;
import com.melo.eapp.entity.Users;
import com.melo.eapp.repository.CartItemRepository;
import com.melo.eapp.repository.OrderRepository;
import com.melo.eapp.repository.ProductRepo;
import com.melo.eapp.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductRepo productRepo;
	
	
	public ResponseEntity<?> addProductToCart(AddProductToCart addProductToCartDto)
	{
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductToCartDto.getUserId(),OrderStatus.Pending);
		Optional<CartItems> optionalCartItems = cartItemRepository.findByProductIdAndOrderIdAndUserId(
				addProductToCartDto.getProductId(),activeOrder.getId(),addProductToCartDto.getUserId());
		
		if(optionalCartItems.isPresent())
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
		else
		{
			Optional<Product> optionalProduct = productRepo.findById(addProductToCartDto.getProductId());
			Optional<Users> optionalUser = userRepository.findById(addProductToCartDto.getUserId());
			
			if(optionalProduct.isPresent() && optionalUser.isPresent())
			{
				CartItems cart = new CartItems();
				cart.setProduct(optionalProduct.get());
				cart.setPrice(optionalProduct.get().getPrice());
				cart.setQuantiy(1L);
				cart.setUser(optionalUser.get());
				cart.setOrder(activeOrder);
				
				CartItems updatedCart = cartItemRepository.save(cart);
				
				activeOrder.setTotalAmount(activeOrder.getTotalAmount() +cart.getPrice());
				activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
				activeOrder.getCartItems().add(cart);
				
				orderRepository.save(activeOrder);
				
				return ResponseEntity.status(HttpStatus.CREATED).body(cart);
				
			}
			else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found.");
			}
		}
	}
	
	
	public OrderDto getCartByUserId(Long userId)
	{
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.Pending);
		
		
		List<CartItemsDto> cartItemDtoList = activeOrder.getCartItems()
				                             .stream()
				                             .map(a -> a.getCartDto()).collect(Collectors.toList());
				
		OrderDto orderDto = new OrderDto();
		orderDto.setAmount(activeOrder.getAmount());
		orderDto.setId(activeOrder.getId());
		orderDto.setOrderStatus(activeOrder.getOrderStatus());
		orderDto.setDiscount(activeOrder.getDiscount());
		orderDto.setTotalAmount(activeOrder.getTotalAmount());
		orderDto.setCartItems(cartItemDtoList);
		return orderDto;
	}
}
