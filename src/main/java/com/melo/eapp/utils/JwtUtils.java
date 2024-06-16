package com.melo.eapp.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	public static final String SECRET ="252502740t7390v8tyn9vyw39ytg8ifehvuirhr3ryh08ry7f08fg7q8g7fa08wg7f";

	
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims,userName);
	}


	@SuppressWarnings("deprecation")
	private String createToken(Map<String, Object> claims, String userName) {
		Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
				.signWith(key,SignatureAlgorithm.HS256).compact();
		
		 System.out.println("Generated JWT Token: " + token); 
		 return token;
	}


	private Key getSignKey() {
		//byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	//	return Keys.hmacShaKeyFor(keyBytes);
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	public String extractUserName(String token)
	{
		return extractClaims(token,Claims ::getSubject);
	}
	
	public <T> T extractClaims(String token,Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}


	private Claims extractAllClaims(String token) {

		try
		{
			token =token.trim();
			return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		}
		catch(Exception e)
		{
			System.out.println("Error occured in extracallclaims method");
			System.out.println(e.getLocalizedMessage()+e.getMessage() +e.getStackTrace());
		}
		return null;
	}
	
	private Boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
		
	}


	private Date extractExpiration(String token) {
		return extractClaims(token, Claims:: getExpiration);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails)
	{
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}
}
