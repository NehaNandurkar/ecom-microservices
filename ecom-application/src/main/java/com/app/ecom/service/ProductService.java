package com.app.ecom.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public ProductResponse createProduct(ProductRequest productRequest) {
		Product product=new Product();
		updateProductFromRequest(product,productRequest);
		Product savedProduct = productRepository.save(product);
		
		return mapToProductResponse(savedProduct);
	}

	private ProductResponse mapToProductResponse(Product savedProduct) {
		ProductResponse productResponse= new ProductResponse();
		productResponse.setId(savedProduct.getId());
		productResponse.setActive(savedProduct.getActive());
		productResponse.setCategory(savedProduct.getCategory());
		productResponse.setDescription(savedProduct.getDescription());
		productResponse.setImageUrl(savedProduct.getImageUrl());
		productResponse.setName(savedProduct.getName());
		productResponse.setPrice(savedProduct.getPrice());
		productResponse.setStockQuantity(savedProduct.getStockQuantity());
		return productResponse;
	}

	private void updateProductFromRequest(Product product, ProductRequest productRequest) {
		product.setCategory(productRequest.getCategory());
		product.setDescription(productRequest.getDescription());
		product.setImageUrl(productRequest.getImageUrl());
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setStockQuantity(productRequest.getStockQuantity());
		
	}

	public Optional<ProductResponse> updateProduct(ProductRequest productRequest, Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
	    
	    if (optionalProduct.isPresent()) {
	    	Product product = optionalProduct.get();
	        updateProductFromRequest(product,productRequest);
	        Product savedProduct = productRepository.save(product);
	        return Optional.of(mapToProductResponse(savedProduct));  
	    }
		return Optional.empty();
	}

}
