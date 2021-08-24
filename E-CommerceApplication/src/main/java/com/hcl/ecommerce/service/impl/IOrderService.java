package com.hcl.ecommerce.service.impl;

import java.util.List;

import com.hcl.ecommerce.dto.OrderResponseDto;
import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.OrderDetails;

public interface IOrderService {
	
	
	public String orderItem(int customerId);
	
	public List<OrderResponseDto> displayOrders(int customerId);


}
