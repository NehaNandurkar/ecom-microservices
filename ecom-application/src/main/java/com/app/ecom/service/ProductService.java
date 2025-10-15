package com.app.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.Product;
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
	
	public List<ProductResponse> getAllProducts() {
	    List<Product> products = productRepository.findByActiveTrue();
	    List<ProductResponse> productResponses = new ArrayList<>();

	    for (Product product : products) {
	        ProductResponse response = mapToProductResponse(product);
	        productResponses.add(response);
	    }

	    return productResponses;
	}

	public boolean deleteProduct(Long id) {
	    Optional<Product> optionalProduct = productRepository.findById(id);

	    if (optionalProduct.isPresent()) {
	        Product product = optionalProduct.get();

	        if (Boolean.TRUE.equals(product.getActive())) {
	            product.setActive(false); 
	            productRepository.save(product);
	            return true; 
	        } else {
	            return false; 
	        }
	    } else {
	        return false; 
	    }
	}

	public List<ProductResponse> searchProducts(String keyword) {
		List<Product> products = productRepository.searchProducts(keyword);
		List<ProductResponse> productResponses=new ArrayList<>();
		for(Product product:products) {
			 ProductResponse response = mapToProductResponse(product);
		     productResponses.add(response);
		}
		return productResponses;
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











}
