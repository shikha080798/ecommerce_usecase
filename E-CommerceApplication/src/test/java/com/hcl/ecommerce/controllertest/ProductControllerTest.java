package com.hcl.ecommerce.controllertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hcl.ecommerce.controller.ProductController;
import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.model.Products;
import com.hcl.ecommerce.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
	
	@Mock
	ProductServiceImpl productServiceImpl;
	
	@InjectMocks
	ProductController productController;
	
	static Products products;
	static ProductResponseDto productResponseDto;
	@BeforeAll
	public static void setUp() {
		products = new Products();
		products.setProductName("Refrigerator");
		products.setDescription("Samsung 192L Single door");
		products.setPrice(14390d);
		
		productResponseDto = new ProductResponseDto();
		productResponseDto.setDescription("Samsung 192L Single door");
		productResponseDto.setPrice(14390d);
		productResponseDto.setProductName("Refrigerator");
	}
	
	@Test
	@DisplayName(value="search products test")
	public void testSearch() {
		List<ProductResponseDto> productList = new ArrayList<ProductResponseDto>();
		productList.add(productResponseDto);
		when(productServiceImpl.searchProduct("Refrigerator")).thenReturn(productList);
		ResponseEntity<List<ProductResponseDto>> list = productController.searchItem("Refrigerator");
		assertEquals(productList,list.getBody());
		assertThat(list.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	@DisplayName(value="search products test:negative scenario")
	public void testSearch1() {
		productResponseDto=new ProductResponseDto();
		List<ProductResponseDto> productList = new ArrayList<ProductResponseDto>();
		when(productServiceImpl.searchProduct("tv")).thenReturn(productList);
		ResponseEntity<List<ProductResponseDto>> list = productController.searchItem("tv");
		assertEquals(productList,list.getBody());
		assertThat(list.getStatusCodeValue()).isEqualTo(200);
	}
	

}
