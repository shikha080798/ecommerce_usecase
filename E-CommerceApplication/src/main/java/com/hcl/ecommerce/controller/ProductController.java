package com.hcl.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.service.impl.ProductServiceImpl;

@RestController
public class ProductController {
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@GetMapping("api/product/{name}")
	public ResponseEntity<List<ProductResponseDto>> searchItem( @PathVariable String name){
		return new ResponseEntity<List<ProductResponseDto>>(productServiceImpl.searchProduct(name),HttpStatus.OK);
	}

}
