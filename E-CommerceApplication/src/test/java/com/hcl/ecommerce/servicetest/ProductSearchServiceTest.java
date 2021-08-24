package com.hcl.ecommerce.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.beans.BeanUtils;

import com.hcl.ecommerce.dao.ProductRepository;
import com.hcl.ecommerce.dto.ProductRequestDto;
import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.exceptions.ProductNotFoundException;
import com.hcl.ecommerce.model.Products;
import com.hcl.ecommerce.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductSearchServiceTest {

	@Mock
	ProductRepository productRepository;

	@InjectMocks
	ProductServiceImpl productServiceImpl;

	static Products products;
	static ProductRequestDto productRequestDto;
	static ProductResponseDto productResponseDto;


	@Test
	@DisplayName(value = "Search Product:Positive scenario")
	public void testSearchProduct() {
		products = new Products();
		List<Products> list = new ArrayList<Products>();
		products.setProductName("Refrigerator");
		products.setDescription("Samsung 192L Single door");
		products.setPrice(14390d);
		list.add(products);
		when(productRepository.findByProductNameContains("Refrigerator")).thenReturn(list);
		assertTrue(list.size() != 0);
		productResponseDto = new ProductResponseDto();
		BeanUtils.copyProperties(products, productResponseDto);
		List<ProductResponseDto> dtoList = new ArrayList<ProductResponseDto>();
		dtoList.add(productResponseDto);
		List<ProductResponseDto> list1 = productServiceImpl.searchProduct("Refrigerator");
		assertEquals(dtoList, list1);
	}

	@Test
	@DisplayName(value = "Search Product:Negative scenario")
	public void testSearchProduct2() {
		List<Products> list = new ArrayList<Products>();
		when(productRepository.findByProductNameContains("tv")).thenReturn(list);
		assertTrue(list.size() == 0);
		assertThrows(ProductNotFoundException.class, () -> productServiceImpl.searchProduct("tv"));

	}
}
