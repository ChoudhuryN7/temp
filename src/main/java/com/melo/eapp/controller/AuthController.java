package com.melo.eapp.controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.melo.eapp.dto.AuthenticationReqDto;
import com.melo.eapp.dto.SignUpRequest;
import com.melo.eapp.dto.UserDto;
import com.melo.eapp.entity.Users;
import com.melo.eapp.repository.UserRepository;
import com.melo.eapp.services.auth.AuthService;
import com.melo.eapp.utils.JwtUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	
	private static final String HEADER_STRING = "Authorization";

	private static final String TOKEN_PREFIX = "Bearer";

	private final AuthenticationManager authenticationManager;
	
	private final UserDetailsService  userDetailsService;
	
	private final JwtUtils jwtUtils;
	
	@Autowired
	private UserRepository userRepository;
	
	private final AuthService authService;
	
	@PostMapping("/authenticate")
	public void createAuthenticationToken(@RequestBody AuthenticationReqDto authenticationReqDto,
			HttpServletResponse res) throws IOException, JSONException
	{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationReqDto.getUserName(), 
					authenticationReqDto.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password");
		}
		
		final UserDetails userDetails =userDetailsService.loadUserByUsername(authenticationReqDto.getUserName());
		Optional<Users> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
		final String jwt =  jwtUtils.generateToken(userDetails.getUsername());
		
		if(optionalUser.isPresent())
		{
			res.getWriter().write(new JSONObject()
					.put("userId",optionalUser.get().getId())
					.put("role",optionalUser.get().getRole())
					.toString());
			
			res.addHeader("Access-Control-Expose-Headers", "Authorization");
			res.addHeader("Access-Control-Allow-Headers", "Authorization,X-PINGOTHER,Origin, "+
			"X-Requested-With,Content-Type,Accept,X-Custom-header");
			res.addHeader(HEADER_STRING,TOKEN_PREFIX+jwt);
		}
		
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest SignUpRequest )
	{
		if(authService.hasUserWithEmail(SignUpRequest.getEmail()))
		{
			return new ResponseEntity<>("User already exist",HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDto userDto = authService.createUser(SignUpRequest);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
}
