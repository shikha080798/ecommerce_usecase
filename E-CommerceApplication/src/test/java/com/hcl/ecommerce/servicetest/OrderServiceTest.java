package com.hcl.ecommerce.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
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
import com.hcl.ecommerce.dao.CartRepository;
import com.hcl.ecommerce.dao.CustomerRepository;
import com.hcl.ecommerce.dao.OrderRepository;
import com.hcl.ecommerce.dao.ProductRepository;
import com.hcl.ecommerce.dto.OrderResponseDto;
import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.exceptions.ValidationExceptionHandler;
import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.Customer;
import com.hcl.ecommerce.model.OrderDetails;
import com.hcl.ecommerce.model.Products;
import com.hcl.ecommerce.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	
	@Mock
	CartRepository cartRepository;
	
	@Mock
	OrderRepository orderRepository;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	ProductRepository productRepository;
	
	@InjectMocks
	OrderServiceImpl orderServiceImpl;
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
		
	}
	
	@Test
	@DisplayName(value="Order Items:Positive scenario")
	public void testOrderItems() {
		
		List<Cart> cartList = new ArrayList<Cart>();
		cartList.add(cart);
		System.out.println(cartList);
	    when(customerRepository.existsById(1)).thenReturn(true);
	    when(cartRepository.displayCart(1)).thenReturn(cartList);
		assertFalse(cartList.isEmpty());
		List<Products> productList=new ArrayList<Products>();
		for(Cart cart:cartList) {
			when(productRepository.findByProductNameAndDescriptionAndPrice(cart.getProductName(),cart.getDescription(),cart.getPrice())).thenReturn(products);
			productList.add(products);
		}	
		
		for(Cart cart1:cartList) {
			orderDetails=new OrderDetails();
			orderDetails.setCreatedDate(new Date()); 
			products = new Products();
			when(productRepository.findByProductNameAndDescriptionAndPrice(cart1.getProductName(),cart1.getDescription(),cart1.getPrice())).thenReturn(products);
			BeanUtils.copyProperties(cart1, orderDetails);
			orderDetails.setProducts(products);
		}
		cartRepository.deleteAll();
	    String result = orderServiceImpl.orderItem(customer.getCustomerId());
	    assertEquals("Your order placed successfully",result);
			
	
	}
	
	@Test
	@DisplayName(value="Order items: negative scenario")
	public void testOrderItems2() {
		
		when(customerRepository.existsById(1)).thenReturn(false);
		assertThrows(ValidationExceptionHandler.class,()->orderServiceImpl.orderItem(1));
		
	}
	
	@Test
	@DisplayName(value="Display Orders :Positive scenario")
	public void testDislay() {
		customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerName("shashi");
		customer.setMailId("shashi@gmail.com");
		customer.setPassword("Shashi@08");
		customer.setPhoneNumber("8908908123");
		products = new Products();
		products = new Products();
		products.setProductId(1);
		products.setProductName("Refrigerator");
		products.setDescription("Samsung 192L Single door");
		products.setPrice(16000d);
		orderDetails = new OrderDetails();
		orderDetails.setCreatedDate(new Date());
		orderDetails.setOrderId(1);
		orderDetails.setCustomer(customer);
		orderDetails.setProducts(products);
		List<OrderDetails> orderList=new ArrayList<OrderDetails>();
		orderList.add(orderDetails);
		assertFalse(orderList.isEmpty());
		when(customerRepository.existsById(1)).thenReturn(true);
		when(orderRepository.displayOrders(1)).thenReturn(orderList);
		List<OrderResponseDto> list = new ArrayList<OrderResponseDto>();
		
		for(OrderDetails order:orderList) {
			OrderResponseDto orderResponseDto = new OrderResponseDto();
			
			when(productRepository.findByProductId(1)).thenReturn(products);
			BeanUtils.copyProperties(order, orderResponseDto);
			BeanUtils.copyProperties(products, orderResponseDto);
			list.add(orderResponseDto);
			}
		List<OrderResponseDto> orderList1 = orderServiceImpl.displayOrders(1);
		assertEquals(list,orderList1);
	}


}
