package com.hcl.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dao.CartRepository;
import com.hcl.ecommerce.dao.CustomerRepository;
import com.hcl.ecommerce.dao.ProductRepository;
import com.hcl.ecommerce.dto.ProductRequestDto;
import com.hcl.ecommerce.dto.ProductResponseDto;
import com.hcl.ecommerce.exceptions.ProductNotFoundException;
import com.hcl.ecommerce.exceptions.ValidationExceptionHandler;
import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.Customer;
import com.hcl.ecommerce.model.Products;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public String addToCart(int customerId, int productId) {
		if(!customerRepository.existsById(customerId)) {
			throw new ValidationExceptionHandler("User Id does not exists");
		}
		if(!productRepository.existsById(productId)) {
			throw new ValidationExceptionHandler("Product does not exists");
		}
		Cart cart = new Cart();
		Products products = new Products();
		Customer customer = new Customer();
		ProductRequestDto productRequestDto = new ProductRequestDto();
		products = productRepository.getById(productId);
		customer = customerRepository.getById(customerId);
			BeanUtils.copyProperties(products, productRequestDto);
			cart.setProductName(productRequestDto.getProductName());
			cart.setDescription(productRequestDto.getDescription());
			cart.setPrice(productRequestDto.getPrice());
			cart.setCustomer(customer);
			cartRepository.save(cart);
			return "Product added to cart successfully";

	}

	@Override
	public String deleteFromCart(int customerId, int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductResponseDto> displayCart(int customerId) {
		if(!customerRepository.existsById(customerId)) {
			throw new ValidationExceptionHandler("User Id does not exists");
		}

		List<ProductResponseDto> list1 = new ArrayList<ProductResponseDto>();
		
		List<Cart> list = cartRepository.displayCart(customerId);
		System.out.println(list);
		if(!list.isEmpty()) {
		for (Cart cart : list) {
			ProductResponseDto productResponseDto = new ProductResponseDto();
			BeanUtils.copyProperties(cart, productResponseDto);
			list1.add(productResponseDto);
		}
		}
		else
			throw new ProductNotFoundException("No items in the cart");
		return list1;
	}

}
