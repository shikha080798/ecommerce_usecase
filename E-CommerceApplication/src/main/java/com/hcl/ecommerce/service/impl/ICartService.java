package com.hcl.ecommerce.service.impl;

import java.util.List;

import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.Products;

public interface ICartService {
	
	public String addToCart(int customerId,int productId);
	
	public String deleteFromCart(int customerId,int productId);
	
	public List<ProductResponseDto> displayCart(int customerId);

}
