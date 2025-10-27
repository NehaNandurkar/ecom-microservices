package com.ecommerce.order.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.order.dtos.CartItemRequest;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.repositories.CartItemRepository;



@Service
@Transactional
public class CartService {
	

	
	@Autowired
	private CartItemRepository cartItemRepository;
	

	public boolean addToCart(String userId, CartItemRequest request) {
//		Optional<Product> productOpt=productRepository.findById(request.getProductId());
//		if(productOpt.isEmpty()) {
//			return false;
//		}
//		Product product =productOpt.get();
//		if(product.getStockQuantity()<request.getQuantity()) {
//			return false;
//		}
		
//		Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
//		if(userOpt.isEmpty()) {
//			return false;
//		}
//	
//		User user =userOpt.get();
		CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItem != null) {
            // Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create new cart item
           CartItem cartItem = new CartItem();
           cartItem.setUserId(userId);
           cartItem.setProductId(request.getProductId());
           cartItem.setQuantity(request.getQuantity());
           cartItem.setPrice(BigDecimal.valueOf(1000.00));
           cartItemRepository.save(cartItem);
        }
        return true;
		
		
	}

	public boolean deleteItemFromCart(String userId, String productId) {
	
		CartItem cartItem=cartItemRepository.findByUserIdAndProductId(userId, productId);
		
		if(cartItem!=null) {
			cartItemRepository.delete(cartItem);
			return true;
		}
		return false;
	}

	public List<CartItem> getCart(String userId) {
		
		return cartItemRepository.findByUserId(userId);
	}

	public void clearCart(String userId) {
		cartItemRepository.deleteByUserId(userId);
		
	}
	
	

}
