package com.melo.eapp.services.admin.category;

import java.util.List;

import com.melo.eapp.dto.CategoryDto;
import com.melo.eapp.entity.Category;

public interface CategoryService {
	
	Category createCategory(CategoryDto categoryDto);

	List<Category> getAllCategory();
}
