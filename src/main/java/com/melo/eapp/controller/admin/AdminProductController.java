package com.melo.eapp.controller.admin;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.eapp.dto.ProductDto;
import com.melo.eapp.services.admin.adminProduct.AdminProductService;


import java.io.IOException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {

	
	private final AdminProductService adminProductService;
	
	
	@PostMapping("/add-product")
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) throws IOException
	{
		ProductDto ProductDto1= adminProductService.addProduct(productDto);
		ProductDto1.setStringImg(productDto.getStringImg());
		return ResponseEntity.status(HttpStatus.CREATED).body(ProductDto1);
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> productDtos = adminProductService.getAllProducts();
		
		return ResponseEntity.ok(productDtos);
	}
	
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name) {
		List<ProductDto> productDtos = adminProductService.getAllProductsByName(name);
		return ResponseEntity.ok(productDtos);
	}
	
	@DeleteMapping("/delete-product/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId)
	{
		boolean deleted = adminProductService.deleteProduct(productId);
				if(deleted)
					return ResponseEntity.noContent().build();
				else
					return ResponseEntity.notFound().build();
	}
}
