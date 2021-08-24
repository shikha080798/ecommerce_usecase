package com.hcl.ecommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.ecommerce.model.Cart;
import com.hcl.ecommerce.model.Products;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	@Query(value="select * from cart c where c.customer_idfk=:customerId",nativeQuery=true)
   List<Cart> displayCart(@Param("customerId") int customerId);

}
