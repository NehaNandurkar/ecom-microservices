package com.app.ecom.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ecom.dto.OrderItemDTO;
import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Order;
import com.app.ecom.model.OrderItem;
import com.app.ecom.model.OrderStatus;
import com.app.ecom.model.User;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Optional<OrderResponse> createOrder(String userId) {
		List<CartItem> cartItems=cartService.getCart(userId);
		if(cartItems.isEmpty()) {
			return Optional.empty();
		}
		
		Optional<User> userOptional=userRepository.findById(Long.valueOf(userId));
		if(userOptional.isEmpty()) {
			return Optional.empty();
		}
		
		User user=userOptional.get();
		
		BigDecimal totalPrice=BigDecimal.ZERO;
		for(CartItem item:cartItems) {
			totalPrice=totalPrice.add(item.getPrice());
		}
		Order order=new Order();
		order.setUser(user);
		order.setStatus(OrderStatus.CONFIRMED);
		order.setTotalAmount(totalPrice);
		List<OrderItem> orderItems=new ArrayList<>();
		for(CartItem item:cartItems) {
			OrderItem orderItem=new OrderItem(
					
					null,
					item.getProduct(),
					item.getQuantity(),
					item.getPrice(),
					order
					);
			orderItems.add(orderItem);
		
	}
		order.setItems(orderItems);
		Order savedOrder=orderRepository.save(order);
		cartService.clearCart(userId);
		
		return Optional.of(mapToOrderResponse(savedOrder));

}

	private OrderResponse mapToOrderResponse(Order order) {
	    List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

	    for (OrderItem orderItem : order.getItems()) {
	        BigDecimal totalPrice = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

	        OrderItemDTO orderItemDTO = new OrderItemDTO(
	                orderItem.getId(),
	                orderItem.getProduct().getId(),
	                orderItem.getQuantity(),
	                orderItem.getPrice(),
	                totalPrice
	        );

	        orderItemDTOList.add(orderItemDTO);
	    }

	    OrderResponse orderResponse = new OrderResponse(
	            order.getId(),
	            order.getTotalAmount(),
	            order.getStatus(),
	            orderItemDTOList,
	            order.getCreatedAt()
	    );

	    return orderResponse;
	}

}
