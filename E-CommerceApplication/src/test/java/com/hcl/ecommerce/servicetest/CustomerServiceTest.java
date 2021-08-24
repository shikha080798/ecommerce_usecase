package com.hcl.ecommerce.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hcl.ecommerce.dao.CustomerRepository;
import com.hcl.ecommerce.dto.CustomerRequestDto;
import com.hcl.ecommerce.exceptions.ValidationExceptionHandler;
import com.hcl.ecommerce.model.Customer;
import com.hcl.ecommerce.service.impl.CustomerServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	
	@Mock
	CustomerRepository customerRepository;
	
	@Autowired
	@InjectMocks
	CustomerServiceImplementation customerServiceImpl;
	
	static CustomerRequestDto customerRequestDto;
	static Customer customer;
	
	@BeforeAll
	 public static void setUp() {
		customerRequestDto = new CustomerRequestDto();
		customerRequestDto.setCustomerName("priya");
		customerRequestDto.setMailId("priya@gmail.com");
		customerRequestDto.setPhoneNumber("9709709420");
		customerRequestDto.setPassword("Priya@08");
		customerRequestDto.setConfirmPassword("Priya@08");
		
		customer = new Customer();
		customer.setCustomerName("priya");
		customer.setMailId("priya@gmail.com");
		customer.setPhoneNumber("9709709420");
		customer.setPassword("Priya@08");
	
		
	}
	
	@Test
	@DisplayName("Login test:Positive scenario")
	public void testLogin() {
		
		when(customerRepository.findByMailIdAndPassword("priya@gmail.com", "Priya@08")).thenReturn(customer);
		System.out.println(customer);
		assertTrue(customer!=null);
		String result = customerServiceImpl.loginCustomer("priya@gmail.com", "Priya@08");
		assertEquals("Login success",result);
		
		
	}
	
	@Test
	@DisplayName("Login test:Negative scenario")
	public void testLogin2() {
		
		when(customerRepository.findByMailIdAndPassword("priya@gmail.com", "Priya@09")).thenReturn(null);

//		System.out.println(customer);
//		assertTrue(customer==null);
//		String result = customerServiceImpl.loginCustomer("hani@gmail.com", "Hani@12");
//		Exception exception = assertThrows(ValidationExceptionHandler.class,()->
//		customerServiceImpl.loginCustomer("hani@gmail.com", "Hani@12"));
//		String expectedMsg="Login failed";
//		String actualMsg=exception.getMessage();
//		assertTrue(actualMsg.contains(expectedMsg));
		assertThrows(ValidationExceptionHandler.class,()->customerServiceImpl.loginCustomer("priya@gmail.com", "Priya@09"));
		
		
	}

}
