package com.hcl.ecommerce.service.impl;

import java.util.List;

import com.hcl.ecommerce.dto.ProductResponseDto;

public interface IProductService {
	
	
	List<ProductResponseDto> searchProduct(String searchItem);

}
