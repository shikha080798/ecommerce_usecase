package com.hcl.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dao.ProductRepository;
import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.exceptions.ProductNotFoundException;
import com.hcl.ecommerce.model.Products;

@Service
public class ProductServiceImpl implements IProductService {
	
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<ProductResponseDto> searchProduct(String searchItem) {
		Products products =new Products();
		
		List<Products> productList=new ArrayList<Products>();
		List<ProductResponseDto> responseList = new ArrayList<ProductResponseDto>();
		productList=productRepository.findByProductNameContains(searchItem);
		System.out.println(productList);
		if(productList.size()!=0) {
		for(Products product:productList) {
			ProductResponseDto productResponse = new ProductResponseDto();
			products.setProductName(product.getProductName());
			products.setDescription(product.getDescription());
			products.setPrice(product.getPrice());
			BeanUtils.copyProperties(products, productResponse);
			System.out.println(product);
			System.out.println(productResponse);
			responseList.add(productResponse);
			System.out.println(responseList);
		}
		return responseList;
		}else
			throw new ProductNotFoundException("Oops! Product does not exist");
	}

	public Products getProductById(int id) {
		return productRepository.getById(id);
	}

}
