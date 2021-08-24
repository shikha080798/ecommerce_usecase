package com.hcl.ecommerce.controllertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.Date;
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

import com.hcl.ecommerce.controller.OrderController;
import com.hcl.ecommerce.dto.OrderResponseDto;
import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.Customer;
import com.hcl.ecommerce.model.OrderDetails;
import com.hcl.ecommerce.model.Products;
import com.hcl.ecommerce.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
	
	@Mock
	OrderServiceImpl orderServiceImpl;
	
	@InjectMocks
	OrderController orderController;
	static Customer customer;
	static Cart cart;
	static Products products;
	static OrderDetails orderDetails;
	
	@BeforeAll
	public static void setUp() {
		customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerName("shashi");
		customer.setMailId("shashi@gmail.com");
		customer.setPassword("Shashi@08");
		customer.setPhoneNumber("8908908123");
		
		cart=new Cart();
		cart.setCustomer(customer);
		cart.setCartId(1);
		cart.setPrice(16000d);
		cart.setProductName("Refrigerator");
		cart.setDescription("Samsung 192L Single door");
		products = new Products();
		products.setProductId(1);
		products.setProductName("Refrigerator");
		products.setDescription("Samsung 192L Single door");
		products.setPrice(16000d);
		orderDetails = new OrderDetails();
		orderDetails.setCreatedDate(new Date());
		orderDetails.setCustomer(customer);
		orderDetails.setOrderId(1);
		orderDetails.setProducts(products);
		
		
	}
	
	@Test
	@DisplayName(value="Order Items :positive scenario")
	public void testOrderItems() {
		when(orderServiceImpl.orderItem(1)).thenReturn("Your order placed successfully");
		ResponseEntity<String> result = orderController.orderItem(1);
		assertEquals("Your order placed successfully",result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	@DisplayName(value="Order Items :negative scenario")
	public void testOrderItems1() {
		List<Cart> list = new ArrayList<Cart>();
		assertTrue(list.isEmpty());
		when(orderServiceImpl.orderItem(1)).thenReturn("Empty cart.. continue shopping");
		ResponseEntity<String> result = orderController.orderItem(1);
		assertEquals("Empty cart.. continue shopping",result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	@DisplayName(value="Display :positive scenario")
	public void testDisplay() {
		List<OrderResponseDto> list = new ArrayList<OrderResponseDto>();
		List<Cart> cartList = new ArrayList<Cart>();
		cartList.add(cart);
		BeanUtils.copyProperties(cartList, list);
		when(orderServiceImpl.displayOrders(1)).thenReturn(list);
		ResponseEntity<List<OrderResponseDto>> result = orderController.displayOrders(1);
		assertEquals(list,result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	@DisplayName(value="Display :negative scenario")
	public void testDisplay2() {
		List<OrderResponseDto> list = new ArrayList<OrderResponseDto>();
		List<Cart> cartList = new ArrayList<Cart>();
		assertTrue(cartList.isEmpty());
		BeanUtils.copyProperties(cartList, list);
		when(orderServiceImpl.displayOrders(1)).thenReturn(list);
		ResponseEntity<List<OrderResponseDto>> result = orderController.displayOrders(1);
		assertEquals(list,result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}


}
