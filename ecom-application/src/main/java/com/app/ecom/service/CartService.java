package com.app.ecom.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;

@Service
@Transactional
public class CartService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private UserRepository userRepository;

	public boolean addToCart(String userId, CartItemRequest request) {
		Optional<Product> productOpt=productRepository.findById(request.getProductId());
		if(productOpt.isEmpty()) {
			return false;
		}
		Product product =productOpt.get();
		if(product.getStockQuantity()<request.getQuantity()) {
			return false;
		}
		
		Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
		if(userOpt.isEmpty()) {
			return false;
		}
	
		User user =userOpt.get();
		CartItem existingCartItem= cartItemRepository.findByUserAndProduct(user,product);
		if(existingCartItem!=null) {
			existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
			existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
			cartItemRepository.save(existingCartItem);
		}else {
			CartItem cartItem=new CartItem();
			cartItem.setUser(user);
			cartItem.setProduct(product);
			cartItem.setQuantity(request.getQuantity());
			cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
			cartItemRepository.save(cartItem);
			
			
		}
		return true;
		
		
	}

	public boolean deleteItemFromCart(String userId, Long productId) {
	
		Optional<Product> productOpt=productRepository.findById(Long.valueOf(productId));	
		Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
		
		if(productOpt.isPresent() && userOpt.isPresent()) {
			cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
			return true;
		}
		return false;
	}

	public List<CartItem> getCart(String userId) {
		
		Optional<User> userOpt=userRepository.findById(Long.valueOf(userId));
		if(userOpt.isPresent()) {
			return cartItemRepository.findByUser(userOpt.get());
		}else {
			return new ArrayList<>();
		}
	}

	public void clearCart(String userId) {
		Optional<User> userOptional =userRepository.findById(Long.valueOf(userId));
		if(userOptional.isPresent()) {
			User user=userOptional.get();
			cartItemRepository.deleteByUser(user);
		}
		
	}
	
	

}
