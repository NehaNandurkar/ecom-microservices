package com.ecommerce.product.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.dtos.ProductRequest;
import com.ecommerce.product.dtos.ProductResponse;
import com.ecommerce.product.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController  {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
		return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest),HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts() {
	    return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
	    return productService.getProductById(id)
	    		.map(ResponseEntity::ok)
	    		.orElseGet(()-> ResponseEntity.notFound().build());
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
	    boolean deleted=productService.deleteProduct(id);
	    if(deleted) {
	    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }else {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	    }
	    
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword) {
	    return new ResponseEntity<>(productService.searchProducts(keyword),HttpStatus.OK);
	    
	}

	

}
