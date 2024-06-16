package com.melo.eapp.services.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.melo.eapp.dto.CategoryDto;
import com.melo.eapp.entity.Category;
import com.melo.eapp.repository.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepo  categoryRepo;
	
	
	
	public Category createCategory(CategoryDto categoryDto)
	{
		Category category =new  Category();
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		
		return categoryRepo.save(category);
	}
	
	public List<Category> getAllCategory()
	{
		return categoryRepo.findAll();
	}
}
