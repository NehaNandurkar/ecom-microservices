package com.ecommerce.order.services;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.order.dtos.OrderItemDTO;
import com.ecommerce.order.dtos.OrderResponse;
import com.ecommerce.order.models.CartItem;
import com.ecommerce.order.models.Order;
import com.ecommerce.order.models.OrderItem;
import com.ecommerce.order.repositories.OrderRepository;
import com.ecommerce.order.models.OrderStatus;



@Service
public class OrderService {

	@Autowired
	private CartService cartService;
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Optional<OrderResponse> createOrder(String userId) {
		List<CartItem> cartItems=cartService.getCart(userId);
		if(cartItems.isEmpty()) {
			return Optional.empty();
		}
		
//		Optional<User> userOptional=userRepository.findById(Long.valueOf(userId));
//		if(userOptional.isEmpty()) {
//			return Optional.empty();
//		}
//		
//		User user=userOptional.get();
		
		BigDecimal totalPrice=BigDecimal.ZERO;
		for(CartItem item:cartItems) {
			totalPrice=totalPrice.add(item.getPrice());
		}
		Order order=new Order();
		order.setUserId(userId);
		order.setStatus(OrderStatus.CONFIRMED);
		order.setTotalAmount(totalPrice);
		List<OrderItem> orderItems=new ArrayList<>();
		for(CartItem item:cartItems) {
			OrderItem orderItem=new OrderItem(
					
					null,
					item.getProductId(),
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
	                orderItem.getProductId(),
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
