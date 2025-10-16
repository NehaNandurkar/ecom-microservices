package com.app.ecom.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.dto.OrderResponse;
import com.app.ecom.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-ID") String userId) {
	    Optional<OrderResponse> orderResponseOpt = orderService.createOrder(userId);

	    if (orderResponseOpt.isPresent()) {
	        return new ResponseEntity<>(orderResponseOpt.get(), HttpStatus.CREATED);
	    } else {
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	}

}
