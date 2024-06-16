package com.melo.eapp.services.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.melo.eapp.entity.Users;
import com.melo.eapp.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Users> optionalUser = userRepository.findFirstByEmail(username);
		
		if(optionalUser.isEmpty()) 
			throw new UsernameNotFoundException("UserName not found",null);
		return new User(optionalUser.get().getEmail(), optionalUser.get().getPassword(), 
				new ArrayList<>());

	}

}
