package com.hcl.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.ecommerce.model.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
	
	List<Products> findByProductNameContains(String searchItem);
	Products findByProductNameAndDescriptionAndPrice(String productName,String description,double price);
    Products findByProductId(int productId);
}
