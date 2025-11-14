package com.ecommerce.user.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	private static Logger logger=LoggerFactory.getLogger(UserController.class);

	// ResponseEntiry is added to have better control on status code
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
		Optional<UserResponse> userResponse = userService.fetchUser(id);

	    if (userResponse.isPresent()) {
	        return new ResponseEntity<>(userResponse.get(), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
		userService.addUser(userRequest);
		return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@RequestBody UserRequest userRequest, @PathVariable String id) {
		logger.info("Request received for user: {}",id);
		Boolean updated = userService.updateUser(id, userRequest);
		if (updated) {
			return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
	}

}
