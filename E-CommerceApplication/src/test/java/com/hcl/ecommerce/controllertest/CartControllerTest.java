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
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

import com.hcl.ecommerce.controller.CartController;
import com.hcl.ecommerce.dto.ProductRequestDto;
import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.Customer;
import com.hcl.ecommerce.model.Products;
import com.hcl.ecommerce.service.impl.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
	
	@Mock
	CartServiceImpl cartServiceImpl;
	
	@InjectMocks
	CartController cartController;
	
	static Cart cart;
	static Customer customer;
	static Products product;
	static ProductRequestDto productRequestDto;
	static ProductResponseDto productResponseDto;
	
	@BeforeAll
	public static void setUp() {
		customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerName("shashi");
		customer.setMailId("shashi@gmail.com");
		customer.setPassword("Shashi@08");
		customer.setPhoneNumber("8908908123");
		
		product = new Products();
		product.setProductId(1);
		product.setProductName("Refrigerator");
		product.setDescription("Samsung 192L Single door");
		product.setPrice(14390d);
		
		cart=new Cart();
		cart.setCustomer(customer);
		cart.setPrice(14390d);
		cart.setProductName("Refrigerator");
		cart.setDescription("Samsung 192L Single door");
	}
	
	@Test
	@DisplayName(value="add to cart")
	public void testAddToCart() {
		when(cartServiceImpl.addToCart(1,1)).thenReturn("Product added to cart successfully");
		ResponseEntity<String> result= cartController.addToCart(1, 1);
		assertEquals("Product added to cart successfully",result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	@DisplayName(value="add to cart")
	public void testAddToCart2() {
		when(cartServiceImpl.addToCart(2,1)).thenReturn("User Id does not exists");
		ResponseEntity<String> result= cartController.addToCart(2, 1);
		assertEquals("User Id does not exists",result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	@DisplayName(value="add to cart")
	public void testAddToCart3() {
		when(cartServiceImpl.addToCart(1,2)).thenReturn("Product does not exists");
		ResponseEntity<String> result= cartController.addToCart(1, 2);
		assertEquals("Product does not exists",result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	
	@Test
	@DisplayName(value="displayCart")
	public void testDisplay() {
		List<ProductResponseDto> cartList = new ArrayList<ProductResponseDto>();
		productResponseDto = new ProductResponseDto();
		List<Cart> cartList1=new ArrayList<Cart>();
		BeanUtils.copyProperties(cartList1, productResponseDto);
		cartList.add(productResponseDto);
		
		when(cartServiceImpl.displayCart(1)).thenReturn(cartList);
		ResponseEntity<List<ProductResponseDto>> result= cartController.displayCart(1);
		assertEquals(cartList,result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	



}
