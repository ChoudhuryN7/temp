package com.melo.eapp.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melo.eapp.dto.CategoryDto;
import com.melo.eapp.entity.Category;
import com.melo.eapp.services.admin.category.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCategoryController {
	
	private final CategoryService categoryService;
	
	
	@PostMapping("/category")
	public ResponseEntity<Category> creeateCategory(@RequestBody CategoryDto categoryDto)
	{
		Category category =categoryService.createCategory(categoryDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(category);
	}
	
	@GetMapping("/get-categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategory());
	}
	

}
