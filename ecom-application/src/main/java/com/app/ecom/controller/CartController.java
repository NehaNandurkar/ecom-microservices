package com.app.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping
	public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,@RequestBody CartItemRequest request){
		if(!cartService.addToCart(userId, request)) {
			return new ResponseEntity<>("Product our of stock or User not found",HttpStatus.BAD_REQUEST);
		}
		cartService.addToCart(userId,request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/items/{productId}")
	public ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,@PathVariable Long productId){
		boolean deleted= cartService.deleteItemFromCart(userId,productId);
		if(deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping
	public ResponseEntity<List<CartItem>> getCart(@RequestHeader("X-User-ID") String userId){
		return new ResponseEntity<>(cartService.getCart(userId),HttpStatus.OK);
	}

}
