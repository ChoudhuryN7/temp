package com.melo.eapp.services.customer;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.melo.eapp.dto.ProductDto;
import com.melo.eapp.entity.Product;
import com.melo.eapp.repository.ProductRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService{
	
	private final  ProductRepo productRepo;
	
	public List<ProductDto> getAllProducts()
	{
		List<Product> products = productRepo.findAll();
		List<ProductDto> response =products.stream().map(a -> a.getDto()).collect(Collectors.toList());
		for(int i=0;i<products.size();i++)
		{
			response.get(i).setStringImg(Base64.getEncoder().encodeToString(products.get(i).getImg()));
		}
		
       return response;
	}
	
	
	public List<ProductDto> getAllProductsByName(String name)
	{
		List<Product> products = productRepo.findAllByNameContaining(name);
		
       return products.stream().map(a -> a.getDto()).collect(Collectors.toList());
	}

}
