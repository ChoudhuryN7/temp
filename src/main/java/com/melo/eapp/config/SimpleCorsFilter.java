package com.melo.eapp.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter{

	@Value("&{app.clinet.url}")
	private String clientAppUrl = "";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		Map<String, String> map = new HashMap<String, String>();
		
		String originHeader = req.getHeader("origin");
		res.setHeader("Access-Control-Allow-Origin", originHeader);
		res.setHeader("Access-Control-Allow-Methods","POST,GET,PUT,OPTIONS,DELETE");
		res.setHeader("Access-Control-Max-Age","3600");
		res.setHeader("Access-Control-Allow-Headers","*");
		
		if("OPTIONS".equalsIgnoreCase(req.getMethod()))
		{
			res.setStatus(HttpServletResponse.SC_OK);
		}else
		{
			chain.doFilter(request, response);
		}
	}

}
