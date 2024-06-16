package com.melo.eapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.melo.eapp.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long>{

	
	List<Product> findAllByNameContaining(String title);
}
