package com.melo.eapp.services.auth;

import com.melo.eapp.dto.SignUpRequest;
import com.melo.eapp.dto.UserDto;

public interface AuthService {

	
	 UserDto createUser(SignUpRequest signUpRequest);

	 boolean hasUserWithEmail(String email);
}
