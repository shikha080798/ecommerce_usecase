package com.hcl.ecommerce.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import com.hcl.ecommerce.dao.CartRepository;
import com.hcl.ecommerce.dao.CustomerRepository;
import com.hcl.ecommerce.dao.ProductRepository;
import com.hcl.ecommerce.dto.ProductRequestDto;
import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.exceptions.ValidationExceptionHandler;
import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.Customer;
import com.hcl.ecommerce.model.Products;
import com.hcl.ecommerce.service.impl.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock 
	CartRepository cartRepository;
	
	@InjectMocks
	CartServiceImpl cartServiceImpl;
	
	static Cart cart;
	static Customer customer;
	static Products product;
	
	
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
		
	}
	
	@Test
	@DisplayName(value="Add to Cart:Positive scenario")
	public void testAddToCart() {
		
		cart = new Cart();
		ProductRequestDto productRequestDto = new ProductRequestDto();
		when(customerRepository.existsById(1)).thenReturn(true);
		when(productRepository.existsById(1)).thenReturn(true);
		when(customerRepository.getById(1)).thenReturn(customer);
		when(productRepository.getById(1)).thenReturn(product);
		
		BeanUtils.copyProperties(product, productRequestDto);
		cart.setCustomer(customer);
		cart.setPrice(productRequestDto.getPrice());
		cart.setProductName(productRequestDto.getProductName());
		cart.setDescription(productRequestDto.getDescription());

		String result = cartServiceImpl.addToCart(1, 1);
		assertEquals("Product added to cart successfully",result);
		
	}
	
	@Test
	@DisplayName(value="Add to Cart:Negative scenario")
	public void testAddToCart2() {
		customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerName("shashi");
		customer.setMailId("shashi@gmail.com");
		customer.setPassword("Shashi@08");
		customer.setPhoneNumber("8908908123");
		
		when(customerRepository.existsById(1)).thenReturn(true);
		when(productRepository.existsById(1)).thenReturn(false);
		assertThrows(ValidationExceptionHandler.class,()->cartServiceImpl.addToCart(1,1));
	}
	
	@Test
	@DisplayName(value="Add to Cart:Negative scenario")
	public void testAddToCart3() {
		product = new Products();
		product.setProductId(1);
		product.setProductName("Refrigerator");
		product.setDescription("Samsung 192L Single door");
		product.setPrice(14390d);
		//when(productRepository.existsById(1)).thenReturn(true);
		when(customerRepository.existsById(1)).thenReturn(false);
		
		assertThrows(ValidationExceptionHandler.class,()->cartServiceImpl.addToCart(1,1));
	}
	
	@Test
	@DisplayName(value="Display Items in cart")
	public void testDisplay() {
		when(customerRepository.existsById(1)).thenReturn(true);
		ProductResponseDto productResponseDto = new ProductResponseDto();
		List<ProductResponseDto> list1 = new ArrayList<ProductResponseDto>();
		List<ProductResponseDto> resultList = new ArrayList<ProductResponseDto>();
		List<Cart> cartList = new ArrayList<Cart>();
		cart.setCustomer(customer);
		cart.setCartId(1);
		cart.setPrice(16000d);
		cart.setProductName("Refrigerator");
		cart.setDescription("192L single door");
		cartList.add(cart);
		for(Cart cart:cartList)
		BeanUtils.copyProperties(cart, productResponseDto);
		list1.add(productResponseDto);
		when(cartRepository.displayCart(1)).thenReturn(cartList); 
		resultList=cartServiceImpl.displayCart(customer.getCustomerId());
		assertEquals(list1,resultList);

		
	}
	
	@Test
	@DisplayName(value="Display Items in cart:Negative scenario")
	public void testDisplay2() {
		when(customerRepository.existsById(1)).thenReturn(false);
		assertThrows(ValidationExceptionHandler.class,()->cartServiceImpl.displayCart(1));
		
	}
		
}