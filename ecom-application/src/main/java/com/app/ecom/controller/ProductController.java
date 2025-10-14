package com.app.ecom.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController  {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
		return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Long id){
		Optional<ProductResponse> updatedProduct = productService.updateProduct(productRequest, id);
	    
	    if (updatedProduct.isPresent()) {
	        return new ResponseEntity<>(updatedProduct.get(), HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	    }
	}

}
