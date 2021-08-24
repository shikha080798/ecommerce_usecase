package com.hcl.ecommerce.controllertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.http.ResponseEntity;

import com.hcl.ecommerce.controller.CustomerController;
import com.hcl.ecommerce.exceptions.ValidationExceptionHandler;
import com.hcl.ecommerce.model.Customer;
import com.hcl.ecommerce.service.impl.CustomerServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
	
	@Mock
	CustomerServiceImplementation customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	static Customer customer;
	
	@BeforeAll
	public static void setUp() {
		customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerName("shashi");
		customer.setMailId("shashi@gmail.com");
		customer.setPassword("Shashi@08");
		customer.setPhoneNumber("8908908123");
		
	}
	
	@Test
	@DisplayName(value="login test:positive scenario")
	public void testLogin() {
		when(customerService.loginCustomer("shashi@gmail.com", "Shashi@08")).thenReturn("Login success");
		ResponseEntity<String> result= customerController.login("shashi@gmail.com", "Shashi@08");
		assertEquals("Login success",result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
		
	}
	
	@Test
	@DisplayName(value="login test:negative scenario")
	public void testLogin2() {
		when(customerService.loginCustomer("shashi@gmail.com", "Shashi@09")).thenReturn("Login failed");
		
		ResponseEntity<String> result= customerController.login("shashi@gmail.com", "Shashi@09");
		assertEquals("Login failed",result.getBody());
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}

}
