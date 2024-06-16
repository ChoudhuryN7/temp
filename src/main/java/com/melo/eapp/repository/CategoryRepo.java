package com.melo.eapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.melo.eapp.entity.Category;

public interface CategoryRepo extends  JpaRepository<Category, Long>{

	

}
