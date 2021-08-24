package com.hcl.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.dto.OrderResponseDto;
import com.hcl.ecommerce.model.OrderDetails;
import com.hcl.ecommerce.service.impl.OrderServiceImpl;

@RestController
public class OrderController {
	
	@Autowired
	OrderServiceImpl orderServiceImpl;
	
	@PostMapping("api/items/{customerId}")
	public ResponseEntity<String> orderItem(@PathVariable int customerId){
		return new ResponseEntity<String>(orderServiceImpl.orderItem(customerId),HttpStatus.OK);
	}
	
	@GetMapping("/api/items/{customerId}")
	public ResponseEntity<List<OrderResponseDto>> displayOrders(@PathVariable int customerId){
		return new ResponseEntity<List<OrderResponseDto>>(orderServiceImpl.displayOrders(customerId),HttpStatus.OK);
	}


}
