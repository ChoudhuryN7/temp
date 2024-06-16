package com.melo.eapp.services.admin.adminProduct;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.melo.eapp.dto.ProductDto;
import com.melo.eapp.entity.Category;
import com.melo.eapp.entity.Product;
import com.melo.eapp.repository.CategoryRepo;
import com.melo.eapp.repository.ProductRepo;

import java.io.IOException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{
	
	private final ProductRepo productRepo;
	
	private final CategoryRepo categoryRepo;
	
	
	public ProductDto addProduct(ProductDto productDto) throws IOException
	{
		Product product = new Product();
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		product.setDescription(productDto.getDescription());
//		product.setImg(productDto.getImage().getBytes());
		
	    // Decode base64-encoded image
       // byte[] decodedImage = Base64.getDecoder().decode(productDto.getByteimg());
		byte[] decodedImage = productDto.getDecodedImage();
        product.setImg(decodedImage);
		Category category = categoryRepo.findById(productDto.getCategoryId()).orElseThrow();
		
		product.setCategory(category);
		
		return productRepo.save(product).getDto();
	}
	
	
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

    public Boolean deleteProduct(Long id)
    {
    	Optional<Product> optionalProduct = productRepo.findById(id);
    	
    	if(optionalProduct.isPresent())
    	{
    		productRepo.deleteById(id);
    		return true;
    	}
    	return false;
    }

}
