package com.ecommerce.order.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String productId;
	private Integer quantity;
	private BigDecimal price;
	@ManyToOne
	@JoinColumn(name="order_id",nullable=false)
	private Order order;
	
	
	public OrderItem() {
		super();
	}
	
	
	public OrderItem(Long id, String productId, Integer quantity, BigDecimal price, Order order) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.order = order;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
}
