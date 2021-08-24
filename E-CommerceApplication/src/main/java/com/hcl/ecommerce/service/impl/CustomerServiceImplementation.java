package com.hcl.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dao.CustomerRepository;
import com.hcl.ecommerce.exceptions.ValidationExceptionHandler;
import com.hcl.ecommerce.model.Customer;

@Service
public class CustomerServiceImplementation implements ICustomerService {
	
	@Autowired
	CustomerRepository customerRespository;

	@Override
	public String loginCustomer(String customerName, String password) {
		Customer customer = new Customer();
		customer = customerRespository.findByMailIdAndPassword(customerName, password);
		if(customer!=null) {
			return "Login success";
		}
		throw new ValidationExceptionHandler("Sorry! User does not exist");
	}

}
