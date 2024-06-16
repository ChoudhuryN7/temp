package com.melo.eapp.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.melo.eapp.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.melo.eapp.services.jwt.UserDetailsServiceImp;

@Component
@RequiredArgsConstructor
public class JwtReqFilter extends OncePerRequestFilter{
	
	
	private final UserDetailsServiceImp  userDetailsService;
	
	private final JwtUtils jwtUtils;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		
		if(authHeader!=null && authHeader.startsWith("Bearer"))
		{
			token = authHeader.substring(6);
			System.out.println("Received token="+token);
			username = jwtUtils.extractUserName(token);
		}
		
		if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails =userDetailsService.loadUserByUsername(username);
			
			if(jwtUtils.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,
						userDetails.getAuthorities());
			    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			    SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		
		filterChain.doFilter(request,response);
	}
	
	

}
