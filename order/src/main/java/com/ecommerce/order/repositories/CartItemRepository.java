package com.ecommerce.order.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ecommerce.order.models.CartItem;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{

	CartItem findByUserIdAndProductId(String userId, String productId);
	
	
	void deleteByUserIdAndProductId(String userId, String productId);


	List<CartItem> findByUserId(String userId);


	void deleteByUserId(String userId);

}
