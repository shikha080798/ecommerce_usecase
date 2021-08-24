package com.hcl.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.hcl.ecommerce.model.OrderDetails;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {
	
	@Query(value="select * from orders c where c.customer_id=:customerId",nativeQuery=true)
	   List<OrderDetails> displayOrders(@Param("customerId") int customerId);
	
	

}
