package com.hcl.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.service.impl.CustomerServiceImplementation;

@RestController
public class CustomerController {
	
	@Autowired 
	CustomerServiceImplementation customerServiceImplementation;
	
	@GetMapping("/api/customer")
	public ResponseEntity<String> login(@Valid @RequestParam String email, @RequestParam String password){
		return new ResponseEntity<String>(customerServiceImplementation.loginCustomer(email, password),HttpStatus.OK);
	}

}
