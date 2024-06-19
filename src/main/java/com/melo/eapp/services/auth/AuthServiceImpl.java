package com.melo.eapp.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.melo.eapp.Enum.OrderStatus;
import com.melo.eapp.Enum.UserRole;
import com.melo.eapp.dto.SignUpRequest;
import com.melo.eapp.dto.UserDto;
import com.melo.eapp.entity.Order;
import com.melo.eapp.entity.Users;
import com.melo.eapp.repository.OrderRepository;
import com.melo.eapp.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder  bCryptPasswordEncoder;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public UserDto createUser(SignUpRequest signUpRequest)
	{
		Users user =new Users();
		
		user.setEmail(signUpRequest.getEmail());
		user.setName(signUpRequest.getName());
		user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(UserRole.CUSTOMER);
		
		Users createdUser = userRepository.save(user);
		
		Order order =new Order();
		order.setAmount(0L);
		order.setTotalAmount(0L);
		order.setDiscount(0L);
		order.setUser(createdUser);
		order.setOrderStatus(OrderStatus.Pending);
		orderRepository.save(order);
		
		
		UserDto userDto =new  UserDto();
		userDto.setId(createdUser.getId());	
		//added by me
		userDto.setEmail(createdUser.getEmail());
		userDto.setName(createdUser.getName());
		userDto.setUserrole(createdUser.getRole());
		
		return userDto;
		
	}

	@Override
	public boolean hasUserWithEmail(String email) {
		
		return userRepository.findFirstByEmail(email).isPresent();
	}
	
	@PostConstruct
	public void createAdminAccount()
	{
		Users adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if(adminAccount==null)
		{
			Users user = new Users();
			user.setEmail("admin@test.com");
			user.setName("admin");
			user.setRole(UserRole.ADMIN);
			user.setPassword(bCryptPasswordEncoder.encode("admin"));
			userRepository.save(user);
		}
	}
}
