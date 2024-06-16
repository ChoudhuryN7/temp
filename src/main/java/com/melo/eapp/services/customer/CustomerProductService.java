package com.melo.eapp.services.customer;

import java.util.List;

import com.melo.eapp.dto.ProductDto;

public interface CustomerProductService {

	
	 
	  List<ProductDto> getAllProducts();
	  
	  List<ProductDto> getAllProductsByName(String name);
}

