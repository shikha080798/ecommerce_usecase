package com.hcl.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dao.CartRepository;
import com.hcl.ecommerce.dao.CustomerRepository;
import com.hcl.ecommerce.dao.OrderRepository;
import com.hcl.ecommerce.dao.ProductRepository;
import com.hcl.ecommerce.dto.OrderResponseDto;
import com.hcl.ecommerce.exceptions.ProductNotFoundException;
import com.hcl.ecommerce.exceptions.ValidationExceptionHandler;
import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.OrderDetails;
import com.hcl.ecommerce.model.Products;

@Service
public class OrderServiceImpl implements IOrderService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public String orderItem(int customerId) {
		
		//List<OrderDetails> orderList = new ArrayList<OrderDetails>();
		if(!customerRepository.existsById(customerId)) {
			throw new ValidationExceptionHandler("User Id does not exists");
		}
		List<Cart> cart = cartRepository.displayCart(customerId);
		List<Products> productList=new ArrayList<Products>();
		if(!cart.isEmpty()) {
		for(Cart itemList:cart) {
			Products products = new Products();
			products=productRepository.findByProductNameAndDescriptionAndPrice(itemList.getProductName(), itemList.getDescription(), itemList.getPrice());
			productList.add(products);
		}
		for(Cart itemList1:cart) {
			OrderDetails orderDetails = new OrderDetails();
			
			orderDetails.setCreatedDate(new Date());
//			orderDetails.setProducts(productList);
//			System.out.println(orderDetails.getProducts());
			Products products = new Products();
			products=productRepository.findByProductNameAndDescriptionAndPrice(itemList1.getProductName(), itemList1.getDescription(), itemList1.getPrice());
			BeanUtils.copyProperties(itemList1,orderDetails);
			orderDetails.setProducts(products);
			orderRepository.saveAndFlush(orderDetails);
			
		}
		cartRepository.deleteAll();
		}else 
			throw new ProductNotFoundException("Empty cart.. continue shopping");
		return "Your order placed successfully";
	}

	@Override
	public List<OrderResponseDto> displayOrders(int customerId) {
		
		if(!customerRepository.existsById(customerId)) {
			throw new ValidationExceptionHandler("User Id does not exists");
		}
		List<OrderResponseDto> list = new ArrayList<OrderResponseDto>();
		List<Products> productList = new ArrayList<Products>();
		List<OrderDetails> orderList = orderRepository.displayOrders(customerId);
		if(!orderList.isEmpty()) {
		for(OrderDetails order:orderList) {
			OrderResponseDto orderResponseDto = new OrderResponseDto();
			Products products = new Products();
			products=productRepository.findByProductId(order.getProducts().getProductId());
			//productList.add(products);
			BeanUtils.copyProperties(order, orderResponseDto);
			BeanUtils.copyProperties(products, orderResponseDto);
			list.add(orderResponseDto);
		}
		}else
			throw new ProductNotFoundException("You haven`t ordered anything yet.. Place your first order");
		return list;
	}

}
