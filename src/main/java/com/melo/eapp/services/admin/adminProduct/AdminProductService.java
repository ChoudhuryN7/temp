package com.melo.eapp.services.admin.adminProduct;

import java.io.IOException;
import java.util.List;

import com.melo.eapp.dto.ProductDto;

public interface AdminProductService {
	
	 ProductDto addProduct(ProductDto productDto)  throws IOException;
	 
	  List<ProductDto> getAllProducts();
	  
	  List<ProductDto> getAllProductsByName(String name);

	  Boolean deleteProduct(Long id);
}
